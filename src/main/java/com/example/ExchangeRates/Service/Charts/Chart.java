package com.example.ExchangeRates.Service.Charts;

import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;

public interface Chart {
    int width = 800; // Ширина изображения
    int height = 400; // Высота изображения
    int annotationFontSize = 13; // Задаем желаемый размер шрифта
    TimeSeriesCollection createDataset();
    JFreeChart createChart();
     byte[] convertImageToByteArray(Period period);

}
