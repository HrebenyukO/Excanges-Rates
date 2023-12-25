package com.example.ExchangeRates.Service.Charts;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.text.SimpleDateFormat;

public interface Chart {
    int width = 800; // Ширина изображения
    int height = 400; // Высота изображения
    int annotationFontSize = 13; // Задаем желаемый размер шрифта
    TimeSeriesCollection dataset();
    JFreeChart chart();
     byte[] convertImageToByteArray();

    default void configurePlot(XYPlot plot, double minBound, double maxBound) {
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerBound(minBound);
        rangeAxis.setUpperBound(maxBound);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setTickUnit(new NumberTickUnit(0.5));

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        plot.setRenderer(renderer);

        plot.setBackgroundPaint(new Color(20, 20, 20));
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);

        DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
        domainAxis.setDateFormatOverride(new SimpleDateFormat("dd.MM"));
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1)); // Установка делений на один день

    }
}
