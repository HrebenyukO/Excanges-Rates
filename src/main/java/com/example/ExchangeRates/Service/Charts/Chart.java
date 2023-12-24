package com.example.ExchangeRates.Service.Charts;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeriesCollection;

public interface Chart {
    int width = 800; // Ширина изображения
    int height = 400; // Высота изображения
    TimeSeriesCollection dataset();
    JFreeChart chart();
     byte[] convertImageToByteArray();
}
