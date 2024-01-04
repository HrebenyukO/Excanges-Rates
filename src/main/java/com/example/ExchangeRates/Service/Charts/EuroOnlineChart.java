package com.example.ExchangeRates.Service.Charts;


import com.example.ExchangeRates.Entity.Currency.NacBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Repository.NacBankRepository;
import com.example.ExchangeRates.Repository.OnlineEuroRepositoryPB;
import org.jfree.chart.JFreeChart;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EuroOnlineChart  implements Chart{
    private OnlineEuroRepositoryPB onlineEuroRepositoryPB;
    private List<OnlineEuroPrivatBank> onlineEuroPrivatBankList;
    private NacBankRepository nacBankRepository;
    private List <NacBank> nacBankList;
    private Period currentPeriod;

    @Autowired
    public EuroOnlineChart(
            OnlineEuroRepositoryPB onlineEuroRepositoryPB,
            NacBankRepository nacBankRepository) {
        this.onlineEuroRepositoryPB = onlineEuroRepositoryPB;
        this.nacBankRepository=nacBankRepository;
        this.onlineEuroPrivatBankList = onlineEuroRepositoryPB.findAll();
        this.nacBankList=nacBankRepository.findAll();
    }
    private Map<String, Double> getActualBound() {
        Map<String, Double> map = new HashMap<>();
        int elementsToSkip;

        switch (currentPeriod) {
            case TEN_DAYS:
                elementsToSkip = Math.max(0, onlineEuroPrivatBankList.size() - 10);
                break;
            case MONTH:
                elementsToSkip = Math.max(0, onlineEuroPrivatBankList.size() - 30);
                break;
            case QUARTER:
                elementsToSkip = Math.max(0, onlineEuroPrivatBankList.size() - 90);
                break;
            default:
                elementsToSkip = 0;
                break;
        }
        // Получение минимального значения
        double minEuroBound = onlineEuroPrivatBankList.stream()
                .skip(elementsToSkip)
                .mapToDouble(OnlineEuroPrivatBank::getOnlinePurchaseEuro)
                .min()
                .orElse(Double.NaN);

        // Получение максимального значения
        double maxEuroBound = onlineEuroPrivatBankList.stream()
                .mapToDouble(OnlineEuroPrivatBank::getOnlineSaleEuro)
                .max()
                .orElse(Double.NaN);

        map.put("min", minEuroBound);
        map.put("max", maxEuroBound);
        return map;
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

    @Override
    public TimeSeriesCollection createDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries onlinePurchaseSeries = new TimeSeries("Купівля");
        TimeSeries onlineSaleSeries = new TimeSeries("Продаж");
        TimeSeries nacBankEuro=new TimeSeries("НацБанк");

        List<NacBank> filteredLists=filterByPeriod(
                nacBankList,
                nacBank -> convertToLocalDate(nacBank.getDate()),
                currentPeriod);
        List<OnlineEuroPrivatBank> filteredList = filterByPeriod(onlineEuroPrivatBankList,
                onlineDollar -> convertToLocalDate(onlineDollar.getDate())  ,
                currentPeriod);

        for (OnlineEuroPrivatBank onlineEuroPrivatBank : filteredList) {
            onlinePurchaseSeries.addOrUpdate(new Day(onlineEuroPrivatBank.getDate()), onlineEuroPrivatBank.getOnlinePurchaseEuro());
            onlineSaleSeries.addOrUpdate(new Day(onlineEuroPrivatBank.getDate()), onlineEuroPrivatBank.getOnlineSaleEuro());
        }
        for(NacBank nacBank:filteredLists){
            nacBankEuro.addOrUpdate(new Day(nacBank.getDate()),nacBank.getEuro());
        }
        dataset.addSeries(onlinePurchaseSeries);
        dataset.addSeries(onlineSaleSeries);
        dataset.addSeries(nacBankEuro);
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
                buildTitle("Онлайн Євро").
                buildMinBound(min).
                buildMaxBound(max).
                buildDataset(dataset).
                buildPeriod(currentPeriod).
                build();
        return chart;
    }
}
