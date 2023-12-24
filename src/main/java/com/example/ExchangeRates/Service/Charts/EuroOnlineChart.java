package com.example.ExchangeRates.Service.Charts;


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

import org.jfree.data.time.Day;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EuroOnlineChart  implements Chart{

    private OnlineEuroRepository onlineEuroRepository;
    private List<OnlineEuro> onlineEuroList;

    private double maxValue;
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
        maxValue=maxSaleEuro;
        return map;
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
    @Override
    public TimeSeriesCollection dataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries onlinePurchaseSeries = new TimeSeries("Купівля");
        TimeSeries onlineSaleSeries = new TimeSeries("Продаж");

        // Добавляем данные во временные ряды, используя addOrUpdate()
        for (OnlineEuro onlineEuro : onlineEuroList) {
            onlinePurchaseSeries.addOrUpdate(new Day(onlineEuro.getDate()), onlineEuro.getOnlinePurchaseEuro());
            onlineSaleSeries.addOrUpdate(new Day(onlineEuro.getDate()), onlineEuro.getOnlineSaleEuro());
        }

        dataset.addSeries(onlinePurchaseSeries);
        dataset.addSeries(onlineSaleSeries);
        return dataset;
    }

    @Override
    public JFreeChart chart() {
        var dataset = dataset();
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

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerBound(minBound); // Начальное значение оси Y
        rangeAxis.setUpperBound(maxBound); // Верхняя граница оси Y
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // Настройка делений оси Y
        rangeAxis.setTickUnit(new NumberTickUnit(0.5));




        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        plot.setRenderer(renderer);

        plot.setBackgroundPaint(new Color(20, 20, 20)); // Пользовательский цвет фона
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.WHITE); // Цвет вертикальных линий
        plot.setDomainGridlinesVisible(true); // Отображаем вертикальные линии
        plot.setRangeGridlinesVisible(true);


        DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
        domainAxis.setDateFormatOverride(new SimpleDateFormat("dd.MM.yyyy")); // Формат даты на оси X



        return chart;
    }
}
