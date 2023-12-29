package com.example.ExchangeRates.Service.Charts;

import com.example.ExchangeRates.Entity.Currency.NacBank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar;
import com.example.ExchangeRates.Repository.NacBankRepository;
import com.example.ExchangeRates.Repository.OnlineDollarRepository;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DollarOnlineChart implements Chart{

    private OnlineDollarRepository onlineDollarRepository;
    private List<OnlineDollar> onlineDollarList;

    private NacBankRepository nacBankRepository;
    private List <NacBank> nacBankList;
    private Period currentPeriod;


    @Autowired
    public DollarOnlineChart(OnlineDollarRepository onlineDollarRepository,NacBankRepository nacBankRepository){
        this.onlineDollarRepository=onlineDollarRepository;
        this.nacBankRepository=nacBankRepository;
        this.onlineDollarList=onlineDollarRepository.findAll();
        this.nacBankList=nacBankRepository.findAll();
    }

    private Map<String,Double> getActualBound(){
        Map<String, Double> map = new HashMap<>();
        // Получение минимального значения
        double minPurchaseEuro = onlineDollarList.stream()
                .mapToDouble(OnlineDollar::getOnlinePurchaseDollar)
                .min()
                .orElse(Double.NaN);

        // Получение максимального значения
        double maxSaleEuro = onlineDollarList.stream()
                .mapToDouble(OnlineDollar::getOnlineSaleDollar)
                .max()
                .orElse(Double.NaN);
        map.put("min", minPurchaseEuro);
        map.put("max", maxSaleEuro);
        return map;
    }
    public TimeSeriesCollection dataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries onlinePurchaseSeries = new TimeSeries("Купівля");
        TimeSeries onlineSaleSeries = new TimeSeries("Продаж");
        TimeSeries nacBankDollar=new TimeSeries("НацБанк");
        List<NacBank> filteredLists=filterByPeriod(
                nacBankList,
                nacBank -> convertToLocalDate(nacBank.getDate()),
                currentPeriod);
        List<OnlineDollar> filteredList = filterByPeriod(onlineDollarList,
                onlineDollar -> convertToLocalDate(onlineDollar.getDate())  ,
                currentPeriod);


        for (OnlineDollar onlineDollar : filteredList) {
            onlinePurchaseSeries.addOrUpdate(new Day(onlineDollar.getDate()), onlineDollar.getOnlinePurchaseDollar());
            onlineSaleSeries.addOrUpdate(new Day(onlineDollar.getDate()), onlineDollar.getOnlineSaleDollar());
        }
        for(NacBank nacBank:filteredLists){
            nacBankDollar.addOrUpdate(new Day(nacBank.getDate()),nacBank.getDollar());
        }
        dataset.addSeries(onlinePurchaseSeries);
        dataset.addSeries(onlineSaleSeries);
        dataset.addSeries(nacBankDollar);
        return dataset;
    }

    // Метод для фильтрации данных по выбранному периоду времени
  /*  private List<OnlineDollar> filterByPeriod(List<OnlineDollar> data, Period period) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate;

        if (period.equals(Period.TEN_DAYS)) {
            startDate = currentDate.minusDays(10);
        } else if (period.equals(Period.MONTH)) {
            startDate = currentDate.minusMonths(1);
        } else if (period.equals(Period.QUARTER)) {
            startDate = currentDate.minusMonths(3);
        } else {
            startDate = currentDate;
        }

        return data.stream()
                .filter(entry -> convertToLocalDate(entry.getDate()).isAfter(startDate))
                .collect(Collectors.toList());
    }*/

    private <T> List<T> filterByPeriod(
            List<T> data,
            Function<T, LocalDate> getDateFunction,
            Period period
    ) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate;

        if (period.equals(Period.TEN_DAYS)) {
            startDate = currentDate.minusDays(10);
        } else if (period.equals(Period.MONTH)) {
            startDate = currentDate.minusMonths(1);
        } else if (period.equals(Period.QUARTER)) {
            startDate = currentDate.minusMonths(3);
        } else {
            startDate = currentDate;
        }

        return data.stream()
                .filter(entry -> getDateFunction.apply(entry).isAfter(startDate))
                .collect(Collectors.toList());
    }

    // Метод для преобразования java.util.Date в LocalDate
    private LocalDate convertToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Override
    public JFreeChart chart() {
        var dataset = dataset();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "ОНЛАЙН ДОЛЛАР ПриватБанк",
                "Дата",
                "Курс",
                dataset,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        double minBound = getActualBound().get("min") - 1;
        double maxBound = getActualBound().get("max") + 1;
        configurePlot(plot,minBound,maxBound);

        // Добавление отметок на кривую с числовыми значениями для первого графика
        TimeSeriesCollection datasetCollection1 = (TimeSeriesCollection) plot.getDataset();
        TimeSeries series1 = datasetCollection1.getSeries(0);

        for (int i = 0; i < series1.getItemCount(); i += 1) {
            double x = dataset.getXValue(0, i);
            double y = series1.getValue(i).doubleValue();

            // Форматирование числа с двумя знаками после запятой
            String formattedY = String.format("%.2f", y);

            XYTextAnnotation annotation = new XYTextAnnotation(formattedY, x, y + 0.2); // Смещение аннотации вверх
            annotation.setPaint(Color.YELLOW); // Установка жёлтого цвета для текста
            annotation.setFont(new Font("SansSerif", Font.PLAIN, annotationFontSize)); // Установка размера шрифта
            plot.addAnnotation(annotation);
        }
        // Добавление отметок на кривую с числовыми значениями для второго графика
        TimeSeriesCollection datasetCollection2 = (TimeSeriesCollection) plot.getDataset();
        TimeSeries series2 = datasetCollection2.getSeries(1);

        for (int i = 0; i < series2.getItemCount(); i += 1) {
            double x = dataset.getXValue(1, i); // Используем другой индекс для второго графика
            double y = series2.getValue(i).doubleValue();

            // Форматирование числа с двумя знаками после запятой
            String formattedY = String.format("%.2f", y);

            XYTextAnnotation annotation = new XYTextAnnotation(formattedY, x, y + 0.2); // Смещение аннотации вверх
            annotation.setPaint(Color.YELLOW); // Установка жёлтого цвета для текста
            annotation.setFont(new Font("SansSerif", Font.PLAIN, annotationFontSize)); // Установка размера шрифта
            plot.addAnnotation(annotation);
        }
        return chart;
    }
    @Override
    public byte[] convertImageToByteArray(Period period) {
        this.currentPeriod=period;
        JFreeChart chart=chart();
        BufferedImage image = chart.createBufferedImage(width, height);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
