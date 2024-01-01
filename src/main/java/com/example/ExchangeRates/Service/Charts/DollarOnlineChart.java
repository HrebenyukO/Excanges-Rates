package com.example.ExchangeRates.Service.Charts;

import com.example.ExchangeRates.Entity.Currency.NacBank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar;
import com.example.ExchangeRates.Repository.NacBankRepository;
import com.example.ExchangeRates.Repository.OnlineDollarRepository;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DollarOnlineChart implements Chart{

    private OnlineDollarRepository onlineDollarRepository;
    private List<OnlineDollar> onlineDollarList;

    private NacBankRepository nacBankRepository;
    private List <NacBank> nacBankList;
    private Period currentPeriod;


    @Autowired
    public DollarOnlineChart(
            OnlineDollarRepository onlineDollarRepository,
            NacBankRepository nacBankRepository){
        this.onlineDollarRepository=onlineDollarRepository;
        this.nacBankRepository=nacBankRepository;
        this.onlineDollarList=onlineDollarRepository.findAll();
        this.nacBankList=nacBankRepository.findAll();
    }

    private Map<String, Double> getActualBound() {
        Map<String, Double> map = new HashMap<>();
        int elementsToSkip;

        switch (currentPeriod) {
            case TEN_DAYS:
                elementsToSkip = Math.max(0, onlineDollarList.size() - 10);
                break;
            case MONTH:
                elementsToSkip = Math.max(0, onlineDollarList.size() - 30);
                break;
            case QUARTER:
                elementsToSkip = Math.max(0, onlineDollarList.size() - 90);
                break;
            default:
                elementsToSkip = 0;
                break;
        }
        // Получение минимального значения
        double minDollarBound = onlineDollarList.stream()
                .skip(elementsToSkip)
                .mapToDouble(OnlineDollar::getOnlinePurchaseDollar)
                .min()
                .orElse(Double.NaN);

        // Получение максимального значения
        double maxDollarBound = onlineDollarList.stream()
                .mapToDouble(OnlineDollar::getOnlineSaleDollar)
                .max()
                .orElse(Double.NaN);

        map.put("min", minDollarBound);
        map.put("max", maxDollarBound);
        return map;
    }
    public TimeSeriesCollection createDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries onlinePurchaseSeries = new TimeSeries("Купівля");
        TimeSeries onlineSaleSeries = new TimeSeries("Продаж");
        TimeSeries nacBankDollar=new TimeSeries("НацБанк");
        List<NacBank> filteredLists=filterByPeriod(
                nacBankList,
                nacBank -> convertToLocalDate(nacBank.getDate()),
                currentPeriod);
        List<OnlineDollar> filteredList = filterByPeriod(onlineDollarList,
                onlineDollar -> convertToLocalDate(onlineDollar.getDate())  ,
                currentPeriod);


        for (OnlineDollar onlineDollar : filteredList) {
            onlinePurchaseSeries.addOrUpdate(new Day(onlineDollar.getDate()), onlineDollar.getOnlinePurchaseDollar());
            onlineSaleSeries.addOrUpdate(new Day(onlineDollar.getDate()), onlineDollar.getOnlineSaleDollar());
        }
        for(NacBank nacBank:filteredLists){
            nacBankDollar.addOrUpdate(new Day(nacBank.getDate()),nacBank.getDollar());
        }
        dataset.addSeries(onlinePurchaseSeries);
        dataset.addSeries(onlineSaleSeries);
        dataset.addSeries(nacBankDollar);
        return dataset;
    }

    private <T> List<T> filterByPeriod(
            List<T> data, Function<T,
            LocalDate> getDateFunction,
            Period period) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate;

        if (period.equals(Period.TEN_DAYS)) {
            startDate = currentDate.minusDays(10);
        } else if (period.equals(Period.MONTH)) {
            startDate = currentDate.minusMonths(1);
        } else if (period.equals(Period.QUARTER)) {
            startDate = currentDate.minusMonths(3);
        } else {
            startDate = currentDate;
        }
        return data.stream()
                .filter(entry -> getDateFunction.apply(entry).isAfter(startDate))
                .collect(Collectors.toList());
    }

    // Метод для преобразования java.util.Date в LocalDate
    private LocalDate convertToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Override
    public JFreeChart createChart() {
        var dataset = createDataset();
        var min=getActualBound().get("min")-1;
        var max=getActualBound().get("max")+1;
        JFreeChart chart = new ChartBuilder().
                buildTitle("Онлайн Доллар").
                buildMinBound(min).
                buildMaxBound(max).
                buildDataset(dataset).
                buildPeriod(currentPeriod).
                build();
        return chart;
    }
    @Override
    public byte[] convertImageToByteArray(Period period) {
        this.currentPeriod=period;
        JFreeChart chart= createChart();
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
