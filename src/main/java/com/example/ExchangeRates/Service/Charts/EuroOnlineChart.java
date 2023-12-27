package com.example.ExchangeRates.Service.Charts;


import com.example.ExchangeRates.Entity.Currency.OnlineDollar;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro;
import com.example.ExchangeRates.Repository.OnlineEuroRepository;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EuroOnlineChart  implements Chart{
    private OnlineEuroRepository onlineEuroRepository;
    private List<OnlineEuro> onlineEuroList;

    @Autowired
    public EuroOnlineChart(OnlineEuroRepository onlineEuroRepository) {
        this.onlineEuroRepository = onlineEuroRepository;
        this.onlineEuroList = onlineEuroRepository.findAll();
    }
    private Map<String,Double> getActualBound(){
        Map<String, Double> map = new HashMap<>();
        // Получение минимального значения
        double minPurchaseEuro = onlineEuroList.stream()
                .mapToDouble(OnlineEuro::getOnlinePurchaseEuro)
                .min()
                .orElse(Double.NaN);
        // Получение максимального значения
        double maxSaleEuro = onlineEuroList.stream()
                .mapToDouble(OnlineEuro::getOnlineSaleEuro)
                .max()
                .orElse(Double.NaN);
        map.put("min", minPurchaseEuro);
        map.put("max", maxSaleEuro);
        return map;
    }
    @Override
    public byte[] convertImageToByteArray(String period) {
        JFreeChart chart=chart(period);
        BufferedImage image = chart.createBufferedImage(width, height);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    @Override
    public TimeSeriesCollection dataset(String period) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries onlinePurchaseSeries = new TimeSeries("Купівля");
        TimeSeries onlineSaleSeries = new TimeSeries("Продаж");


        List<OnlineEuro> filteredList = filterByPeriod(onlineEuroList,period);
        // Добавляем данные во временные ряды, используя addOrUpdate()
        for (OnlineEuro onlineEuro : filteredList) {
            onlinePurchaseSeries.addOrUpdate(new Day(onlineEuro.getDate()), onlineEuro.getOnlinePurchaseEuro());
            onlineSaleSeries.addOrUpdate(new Day(onlineEuro.getDate()), onlineEuro.getOnlineSaleEuro());
        }
        dataset.addSeries(onlinePurchaseSeries);
        dataset.addSeries(onlineSaleSeries);
        return dataset;
    }
    private List<OnlineEuro> filterByPeriod(List<OnlineEuro> data, String period) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate;

        if (period.equals("10_days")) {
            startDate = currentDate.minusDays(10);
        } else if (period.equals("month")) {
            startDate = currentDate.minusMonths(1);
        } else if (period.equals("quarter")) {
            startDate = currentDate.minusMonths(3);
        } else {
            startDate = currentDate;
        }

        return data.stream()
                .filter(entry -> convertToLocalDate(entry.getDate()).isAfter(startDate))
                .collect(Collectors.toList());
    }

    // Метод для преобразования java.util.Date в LocalDate
    private LocalDate convertToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Override
    public JFreeChart chart(String period) {
        var dataset = dataset(period);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "ОНЛАЙН ЄВРО ПриватБанк",
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
}
