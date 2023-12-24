package com.example.ExchangeRates.Service.Charts;

import com.example.ExchangeRates.Entity.Currency.OnlineDollar;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro;
import com.example.ExchangeRates.Repository.OnlineEuroRepository;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EuroOnlineChart  implements Chart{

    @Autowired
    private OnlineEuroRepository onlineEuroRepository;

    private List<OnlineEuro> getAllOnlineEuro(){

        return onlineEuroRepository.findAll();
    }

    private Map<String,Double> getActualBound(){
        Map<String, Double> map = new HashMap<>();
        List<OnlineEuro> onlineEuroList=getAllOnlineEuro();

        // Получение минимального значения
        double minSaleEuro = onlineEuroList.stream()
                .mapToDouble(OnlineEuro::getOnlineSaleEuro)
                .min()
                .orElse(Double.NaN);

        // Получение максимального значения
        double maxSaleEuro = onlineEuroList.stream()
                .mapToDouble(OnlineEuro::getOnlineSaleEuro)
                .max()
                .orElse(Double.NaN);

        map.put("min", minSaleEuro);
        map.put("max", maxSaleEuro);
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
    public TimeSeriesCollection  dataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        var onlineEuros = onlineEuroRepository.findAll();

        // Создаем временные ряды для курса валюты при покупке и продаже онлайн
        TimeSeries onlinePurchaseSeries = new TimeSeries("Online Purchase Euro");
        TimeSeries onlineSaleSeries = new TimeSeries("Online Sale Euro");

        // Добавляем данные во временные ряды
        for (OnlineEuro onlineEuro : onlineEuros) {
            onlinePurchaseSeries.add(new Day(onlineEuro.getDate()), onlineEuro.getOnlinePurchaseEuro());
            onlineSaleSeries.add(new Day(onlineEuro.getDate()), onlineEuro.getOnlineSaleEuro());
        }

        // Добавляем временные ряды в коллекцию
        dataset.addSeries(onlinePurchaseSeries);
        dataset.addSeries(onlineSaleSeries);

        return dataset;
    }

    @Override
    public JFreeChart chart(){
        var dataset = dataset();
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Євро онлайн ПриватБанк",
                "Date",
                "Курс Євро онлайн",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = chart.getXYPlot();

        double minBound=getActualBound().get("min")-1;
        double maxBound=getActualBound().get("max")+1;

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerBound(minBound); // Начальное значение оси Y
        rangeAxis.setUpperBound(maxBound); // Верхняя граница оси Y
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // Настройка делений оси Y


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
        return chart;
    }
}
