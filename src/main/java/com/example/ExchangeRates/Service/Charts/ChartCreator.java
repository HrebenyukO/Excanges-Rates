package com.example.ExchangeRates.Service.Charts;

import com.example.ExchangeRates.Entity.Period;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;

import java.util.List;

public interface ChartCreator <T>  {
    int width = 800; // Ширина изображения
    int height = 400; // Высота изображения
    TimeSeriesCollection createDataset();
    //TimeSeriesCollection createDatasetWithArchive(List<T> archiveData, Period currentPeriod);
    JFreeChart createChart();
     byte[] convertImageToByteArray(Period period,Class<T> entityClass);

}
