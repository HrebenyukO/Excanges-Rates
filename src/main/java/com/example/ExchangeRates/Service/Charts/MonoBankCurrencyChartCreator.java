package com.example.ExchangeRates.Service.Charts;

import com.example.ExchangeRates.Entity.Currency.NacBank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuroMonoBank;
import com.example.ExchangeRates.Entity.Period;
import com.example.ExchangeRates.Service.Currency.MonoBankCurrencyBeanService;
import com.example.ExchangeRates.Service.Currency.NacBankCurrencyBeanService;
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
import java.util.stream.Stream;
@Component
public class MonoBankCurrencyChartCreator <T> implements ChartCreator<T>{
    Period currentPeriod;
    List<T> currencyMonobankList;
    @Autowired
    MonoBankCurrencyBeanService monoBankCurrencyBeanService;
    @Autowired
    NacBankCurrencyBeanService nacBankCurrencyBeanService;

    @Override
    public TimeSeriesCollection createDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries onlinePurchaseSeries = new TimeSeries("Купівля");
        TimeSeries onlineSaleSeries = new TimeSeries("Продаж");
        TimeSeries nacBankDollar = new TimeSeries("НацБанк");

        var nacBankList = nacBankCurrencyBeanService.findAll();
        List<NacBank> filteredLists = filterByPeriod(
                nacBankList,
                nacBank -> convertToLocalDate(nacBank.getDate()),
                currentPeriod);



        if (!currencyMonobankList.isEmpty()) {
            if (currencyMonobankList.get(0) instanceof OnlineDollarMonobank) {
                List<OnlineDollarMonobank> filteredList = filterByPeriod(
                        (List<OnlineDollarMonobank>) currencyMonobankList,
                        onlineDollar -> convertToLocalDate(onlineDollar.getDate()),
                        currentPeriod);
                for (OnlineDollarMonobank onlineDollarMonobank : filteredList) {
                    onlinePurchaseSeries.addOrUpdate(new Day(onlineDollarMonobank.getDate()),
                            onlineDollarMonobank.getOnlinePurchaseDollar());
                    onlineSaleSeries.addOrUpdate(new Day(onlineDollarMonobank.getDate()),
                            onlineDollarMonobank.getOnlineSaleDollar());
                }
                for (NacBank nacBank : filteredLists) {
                    nacBankDollar.addOrUpdate(new Day(nacBank.getDate()), nacBank.getDollar());
                }
            } else if (currencyMonobankList.get(0) instanceof OnlineEuroMonoBank) {
                List<OnlineEuroMonoBank> filteredList = filterByPeriod(
                        (List<OnlineEuroMonoBank>) currencyMonobankList,
                        onlineEuro -> convertToLocalDate(onlineEuro.getDate()),
                        currentPeriod);
                for (OnlineEuroMonoBank onlineEuroMonoBank : filteredList) {
                    onlinePurchaseSeries.addOrUpdate(new Day(onlineEuroMonoBank.getDate()),
                            onlineEuroMonoBank.getOnlinePurchaseEuro());
                    onlineSaleSeries.addOrUpdate(new Day(onlineEuroMonoBank.getDate()),
                            onlineEuroMonoBank.getOnlineSaleEuro());
                }
                for (NacBank nacBank : filteredLists) {
                    nacBankDollar.addOrUpdate(new Day(nacBank.getDate()), nacBank.getEuro());
                }
            }
        }
        dataset.addSeries(onlinePurchaseSeries);
        dataset.addSeries(onlineSaleSeries);
        dataset.addSeries(nacBankDollar);
        return dataset;
    }
    @Override
    public JFreeChart createChart() {
        String tittle=null;
        if(currencyMonobankList.get(0).equals(OnlineDollarMonobank.class)){
            tittle="Онлайн Доллар";}
        else tittle="Євро Онлайн";
        var dataset = createDataset();
        var min=getActualBound().get("min")-1;
        var max=getActualBound().get("max")+1;
        JFreeChart chart = new ChartBuilder().
                buildTitle(tittle).
                buildMinBound(min).
                buildMaxBound(max).
                buildDataset(dataset).
                buildPeriod(currentPeriod).
                build();
        return chart;
    }

    @Override
    public byte[] convertImageToByteArray(Period period,Class<T> entityClass) {
        this.currentPeriod=period;
        if (entityClass.equals(OnlineDollarMonobank.class)) {
            this.currencyMonobankList = (List<T>) monoBankCurrencyBeanService.
                    findAllOnlineDollarMonobank();
        } else if (entityClass.equals(OnlineEuroMonoBank.class)) {
            this.currencyMonobankList = (List<T>) monoBankCurrencyBeanService.
                    findAllOnlineEuroMonobank();
        }
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

    private Map<String, Double> getActualBound() {
        Map<String, Double> map = new HashMap<>();
        int elementsToSkip;

        switch (currentPeriod) {
            case TEN_DAYS:
                elementsToSkip = Math.max(0, currencyMonobankList.size() - 10);
                break;
            case MONTH:
                elementsToSkip = Math.max(0, currencyMonobankList.size() - 30);
                break;
            case QUARTER:
                elementsToSkip = Math.max(0, currencyMonobankList.size() - 90);
                break;
            default:
                elementsToSkip = 0;
                break;
        }
        double minCurrencyBound = 0.0;
        double maxCurrencyBound = 0.0;
        Stream<Double> purchaseStream;
        Stream<Double> saleStream;

        if (!currencyMonobankList.isEmpty()) {
            if (currencyMonobankList.get(0) instanceof OnlineDollarMonobank) {
                purchaseStream = currencyMonobankList.stream()
                        .map(OnlineDollarMonobank.class::cast)
                        .map(OnlineDollarMonobank::getOnlinePurchaseDollar);
                saleStream = currencyMonobankList.stream()
                        .map(OnlineDollarMonobank.class::cast)
                        .map(OnlineDollarMonobank::getOnlineSaleDollar);
            } else if (currencyMonobankList.get(0) instanceof OnlineEuroMonoBank) {
                purchaseStream = currencyMonobankList.stream()
                        .map(OnlineEuroMonoBank.class::cast)
                        .map(OnlineEuroMonoBank::getOnlinePurchaseEuro);
                saleStream = currencyMonobankList.stream()
                        .map(OnlineEuroMonoBank.class::cast)
                        .map(OnlineEuroMonoBank::getOnlineSaleEuro);
            } else {
                return map;
            }

            minCurrencyBound = purchaseStream
                    .skip(elementsToSkip)
                    .mapToDouble(Double::doubleValue)
                    .min()
                    .orElse(Double.NaN);
            maxCurrencyBound = saleStream
                    .mapToDouble(Double::doubleValue)
                    .max()
                    .orElse(Double.NaN);
        }
        map.put("min", minCurrencyBound);
        map.put("max", maxCurrencyBound);
        return map;
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

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
