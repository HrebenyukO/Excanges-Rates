package com.example.ExchangeRates.Service.Charts;

import com.example.ExchangeRates.Entity.Period;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.VerticalAlignment;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.*;
import java.text.SimpleDateFormat;


public class ChartBuilder {
    private String timeAxisLabel = "Дата";
    private String valueAxisLabel = "Курс";
    private int annotationFontSize = 13;
    private boolean legend = true;
    private boolean tooltips = true;
    private boolean urls = false;
    private String title;

    private Title subTitle;
    private TimeSeriesCollection dataset;
    private Period period;
    private double minBound;
    private double maxBound;

    public ChartBuilder buildTitle(String title) {
        this.title = title;
        return this;
    }

    public ChartBuilder buildMinBound(double min) {
        this.minBound = min;
        return this;
    }
    public ChartBuilder buildSubTitle(String subtitle) {
        this.subTitle = new TextTitle(
                subtitle,
                new Font("SansSerif", Font.BOLD + Font.ITALIC, 15));
        this.subTitle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        this.subTitle.setVerticalAlignment(VerticalAlignment.TOP);
        return this;
    }
    public ChartBuilder buildMaxBound(double max) {
        this.maxBound = max;
        return this;
    }

    public ChartBuilder buildDataset(TimeSeriesCollection dataset) {
        this.dataset = dataset;
        return this;
    }

    public ChartBuilder buildPeriod(Period period) {
        this.period = period;
        return this;
    }

    public JFreeChart build() {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title,
                timeAxisLabel,
                valueAxisLabel,
                dataset,
                legend,
                tooltips,
                urls
        );
        XYPlot plot = chart.getXYPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerBound(minBound);// Начальное значение оси Y
        rangeAxis.setUpperBound(maxBound);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setTickUnit(new NumberTickUnit(0.5));

        chart.addSubtitle(subTitle);


        // Настройка линий графика
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.BLUE); // Устанавливаем синий цвет для третьего графика
        renderer.setSeriesStroke(2, new BasicStroke(1.0f));
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        plot.setRenderer(renderer);

        //Натсройка фона и отображение разметки
        plot.setBackgroundPaint(new Color(20, 20, 20));
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);

        // Добавление отметок на кривую с числовыми значениями для первого графика
        TimeSeriesCollection datasetCollection1 = (TimeSeriesCollection) plot.getDataset();

        // Добавление отметок на кривую с числовыми значениями для второго графика
        TimeSeriesCollection datasetCollection2 = (TimeSeriesCollection) plot.getDataset();

        // Установка интервалов оси x
        DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
        domainAxis.setDateFormatOverride(new SimpleDateFormat("dd.MM"));
        switch (period) {
            case TEN_DAYS:
                domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1)); // Устанавливаем интервал в один день
                addAnnotationsToSeries(plot, datasetCollection1, 0, 1); // Выводить каждую точку
                addAnnotationsToSeries(plot, datasetCollection2, 1, 1);
                break;
            case MONTH:
                domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 2)); // Устанавливаем интервал в два дня
                addAnnotationsToSeries(plot, datasetCollection1, 0, 2); // Выводить через 3 точки
                addAnnotationsToSeries(plot, datasetCollection2, 1, 2);
                break;
            case QUARTER:
                domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 5)); // Устанавливаем интервал в 5 дней
                addAnnotationsToSeries(plot, datasetCollection1, 0, 5); // Выводить через 5 точек
                addAnnotationsToSeries(plot, datasetCollection2, 1, 5);
                break;
        }
        return chart;
    }
    //  Отображение отметок на кривой графика
    private void addAnnotationsToSeries(XYPlot plot, TimeSeriesCollection datasetCollection, int seriesIndex, int skip) {
        TimeSeries series = datasetCollection.getSeries(seriesIndex);
        for (int i = 0; i < series.getItemCount(); i += skip) {
            double x = dataset.getXValue(seriesIndex, i);
            double y = series.getValue(i).doubleValue();
            String formattedY = String.format("%.2f", y);
            XYTextAnnotation annotation = new XYTextAnnotation(formattedY, x, y + 0.2);
            annotation.setPaint(Color.YELLOW);
            annotation.setFont(new Font("SansSerif", Font.PLAIN, annotationFontSize));
            plot.addAnnotation(annotation);
        }
    }
}