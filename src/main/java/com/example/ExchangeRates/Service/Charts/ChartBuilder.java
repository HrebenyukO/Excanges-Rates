package com.example.ExchangeRates.Service.Charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.*;


public class ChartBuilder {
    private String timeAxisLabel="Дата";
    private String valueAxisLabel="Курс";
    private int annotationFontSize=13;
    private boolean legend=true;
    private boolean tooltips=true;
    private boolean urls=false;
    private String title;
    private TimeSeriesCollection dataset;
    private Period period;
    private double minBound;
    private double maxBound;

    public ChartBuilder buildTitle(String title) {
        this.title = title;
        return this;
    }
    public ChartBuilder buildMinBound(double min){
        this.minBound=min;
        return this;
    }
    public ChartBuilder buildMaxBound(double max){
        this.maxBound=max;
        return this;
    }
    public ChartBuilder buildDataset(TimeSeriesCollection dataset) {
        this.dataset = dataset;
        return this;
    }
    public ChartBuilder buildPeriod(Period period){
        this.period=period;
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
        rangeAxis.setLowerBound(minBound);
        rangeAxis.setUpperBound(maxBound);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setTickUnit(new NumberTickUnit(0.5));

        // Настройка линий графика
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
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
        addAnnotationsToSeries(plot, datasetCollection1, 0);

        // Добавление отметок на кривую с числовыми значениями для второго графика
        TimeSeriesCollection datasetCollection2 = (TimeSeriesCollection) plot.getDataset();
        addAnnotationsToSeries(plot, datasetCollection2, 1);
        return chart;
    }

    //  Отображение отметок на кривой графика
    private void addAnnotationsToSeries(XYPlot plot, TimeSeriesCollection datasetCollection, int seriesIndex) {
        TimeSeries series = datasetCollection.getSeries(seriesIndex);

        for (int i = 0; i < series.getItemCount(); i++) {
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
