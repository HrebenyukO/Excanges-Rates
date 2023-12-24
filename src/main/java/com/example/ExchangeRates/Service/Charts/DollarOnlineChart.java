package com.example.ExchangeRates.Service.Charts;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DollarOnlineChart implements Chart{

    OnlineDollarRepository onlineDollarRepository;
    private final List<OnlineDollar> onlineDollarList;

    private static double maxValue;

    @Autowired
    public DollarOnlineChart(OnlineDollarRepository onlineDollarRepository,
                             List<OnlineDollar> onlineDollarList){
        this.onlineDollarRepository=onlineDollarRepository;
        this.onlineDollarList=onlineDollarRepository.findAll();
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
        maxValue=maxSaleEuro;
        return map;
    }
    @Override
    public TimeSeriesCollection dataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries onlinePurchaseSeries = new TimeSeries("Купівля");
        TimeSeries onlineSaleSeries = new TimeSeries("Продаж");
        // Добавляем данные во временные ряды, используя addOrUpdate()
        for (OnlineDollar onlineEuro : onlineDollarList) {
            onlinePurchaseSeries.addOrUpdate(new Day(onlineEuro.getDate()), onlineEuro.getOnlinePurchaseDollar());
            onlineSaleSeries.addOrUpdate(new Day(onlineEuro.getDate()), onlineEuro.getOnlineSaleDollar());
        }
        dataset.addSeries(onlinePurchaseSeries);
        dataset.addSeries(onlineSaleSeries);
        return dataset;
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
    public byte[] convertImageToByteArray() {
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
