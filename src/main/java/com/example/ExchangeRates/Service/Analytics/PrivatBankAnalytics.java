package com.example.ExchangeRates.Service.Analytics;

import com.example.ExchangeRates.Entity.Currency.CashDollar;
import com.example.ExchangeRates.Entity.Currency.CashEuro;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro;
import com.example.ExchangeRates.Repository.CashDollarRepository;
import com.example.ExchangeRates.Repository.CashEuroRepository;
import com.example.ExchangeRates.Repository.OnlineDollarRepository;
import com.example.ExchangeRates.Repository.OnlineEuroRepository;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PrivatBankAnalytics {
    private CashEuroRepository cashEuroRepository;

    private CashDollarRepository cashDollarRepository;

    private OnlineDollarRepository onlineDollarRepository;

    private OnlineEuroRepository onlineEuroRepository;

    PrivatBankAnalytics(CashEuroRepository cashEuroRepository,
                        CashDollarRepository cashDollarRepository,
                        OnlineDollarRepository onlineDollarRepository,
                        OnlineEuroRepository onlineEuroRepository){
        this.cashEuroRepository=cashEuroRepository;
        this.cashDollarRepository=cashDollarRepository;
        this.onlineDollarRepository=onlineDollarRepository;
        this.onlineEuroRepository=onlineEuroRepository;
    }

    private List<CashDollar> cashDollarList;
    private List<CashEuro> cashEuroList;
    private List<OnlineDollar> onlineDollarList;
    private  List<OnlineEuro> onlineEuroList;

    private List<CashDollar> getAllCashDollar(){
        return cashDollarRepository.findAll();
    }
    private List<CashEuro> getAllCashEuro(){
        return cashEuroRepository.findAll();
    }
    private List<OnlineDollar> getAllOnlineDollar(){
        return onlineDollarRepository.findAll();
    }

    private List<OnlineEuro> getAllOnlineEuro(){
        return onlineEuroRepository.findAll();
    }
   /* public InputFile sendChart() {
        DefaultCategoryDataset dataset = getDefaultCategoryDataset();
        JFreeChart lineChart=createJFreeChart(dataset);
        try {
            File imageFile = new File("chart.png");
            ChartUtils.saveChartAsPNG(imageFile, lineChart, 600, 400);
            return new InputFile(imageFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    // Добавление данных
    public DefaultCategoryDataset getDefaultCategoryDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        var onlineDollarList=onlineDollarRepository.findAll();
        for (OnlineDollar onlineDollar : onlineDollarList) {
            dataset.addValue(onlineDollar.getOnlinePurchaseDollar(), "Online Purchase Dollar", onlineDollar.getDate());
            dataset.addValue(onlineDollar.getOnlineSaleDollar(),"Online Sale Dollar",onlineDollar.getDate());
        }
        return dataset;
    }


    private JFreeChart createJFreeChart(DefaultCategoryDataset dataset) {
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Курси валют у ПриватБанку",
                "Date",
                "Dollar Online",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        CategoryPlot plot = lineChart.getCategoryPlot();
        // Настройка стиля линии
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        customizeFunction1(renderer);
        customizeFunction2(renderer);

        // Настройка цвета фона и сетки
        plot.setBackgroundPaint(new Color(20, 20, 20)); // Пользовательский цвет фона
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.WHITE); // Цвет вертикальных линий
        plot.setDomainGridlinesVisible(true); // Отображаем вертикальные линии
        plot.setRangeGridlinesVisible(true);


        // Настройка осей
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0.02); // Маржа в начале оси X
        domainAxis.setUpperMargin(0.02); // Маржа в конце оси X

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerBound(35); // Начальное значение оси Y
        rangeAxis.setUpperBound(39); // Верхняя граница оси Y
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // Настройка делений оси Y

        // Настройка заголовка
        TextTitle title = lineChart.getTitle();
        title.setPaint(Color.DARK_GRAY);
        title.setFont(new Font("Arial", Font.BOLD, 24));

       /* // Добавление легенды
        LegendTitle legend = new LegendTitle(plot.getRenderer());
        legend.setItemFont(new Font("Arial", Font.PLAIN, 14)); // Шрифт подписей к кривым
        legend.setBorder(1.0, 1.0, 1.0, 1.0); // Границы легенды
        legend.setBackgroundPaint(Color.BLUE); // Цвет фона легенды
        lineChart.addLegend(legend);*/

        // Настройка цветов
        renderer.setSeriesPaint(0, new Color(20, 20, 20)); // Пользовательский цвет линии
        renderer.setSeriesShapesVisible(2,true);
        // Настройка оси X
        domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12)); // Шрифт подписей оси X
        domainAxis.setTickLabelPaint(Color.BLACK); // Цвет подписей оси X

        // Настройка оси Y
        rangeAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12)); // Шрифт подписей оси Y
        rangeAxis.setTickLabelPaint(Color.BLACK); // Цвет подписей оси Y
        lineChart.fireChartChanged();
        return lineChart;
    }

    private void customizeFunction1(LineAndShapeRenderer renderer) {
        renderer.setSeriesStroke(0, new BasicStroke(8.0f)); // Установка толщины линии
        renderer.setSeriesPaint(0, Color.RED); // Установка цвета линии для первой функции
    }

    private void customizeFunction2(LineAndShapeRenderer renderer) {
        renderer.setSeriesStroke(1, new BasicStroke(4.0f)); // Установка толщины линии
        renderer.setSeriesPaint(1, Color.GREEN); // Установка цвета линии для второй функции
    }

    public byte[] convertImageToByteArray() {
        int width = 800; // Ширина изображения
        int height = 400; // Высота изображения
        var dataset=getDefaultCategoryDataset();
        JFreeChart chart = createJFreeChart(dataset);
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
