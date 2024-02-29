package com.example.ExchangeRates.Service.Charts;

import com.example.ExchangeRates.Entity.Currency.*;
import com.example.ExchangeRates.Entity.Currency.ArсhiveCurrency.PrivatBankCurrencyArchive;
import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarPrivatBank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroPrivatBank;
import com.example.ExchangeRates.Entity.Period;
import com.example.ExchangeRates.Service.Currency.NacBankCurrencyBeanService;
import com.example.ExchangeRates.Service.Currency.PrivatBankCurrencyBeanService;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PrivatBankCurrencyChartCreator<T> implements ChartCreator<T> {

    Period currentPeriod;
    List<T> currencyPrivatBankList;
    @Autowired
    PrivatBankCurrencyBeanService privatBankCurrencyBeanService;
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
        if (!currencyPrivatBankList.isEmpty()) {
            if (currencyPrivatBankList.get(0) instanceof OnlineDollarPrivatBank) {
                List<OnlineDollarPrivatBank> filteredList = filterByPeriod(
                        (List<OnlineDollarPrivatBank>) currencyPrivatBankList,
                        onlineDollar -> convertToLocalDate(onlineDollar.getDate()),
                        currentPeriod);
                for (OnlineDollarPrivatBank onlineDollarPrivatBank : filteredList) {
                    onlinePurchaseSeries.addOrUpdate(new Day(onlineDollarPrivatBank.getDate()),
                            onlineDollarPrivatBank.getOnlinePurchaseDollar());
                    onlineSaleSeries.addOrUpdate(new Day(onlineDollarPrivatBank.getDate()),
                            onlineDollarPrivatBank.getOnlineSaleDollar());
                }
                for (NacBank nacBank : filteredLists) {
                    nacBankDollar.addOrUpdate(new Day(nacBank.getDate()), nacBank.getDollar());
                }
            } else if (currencyPrivatBankList.get(0) instanceof OnlineEuroPrivatBank) {
                List<OnlineEuroPrivatBank> filteredList = filterByPeriod(
                        (List<OnlineEuroPrivatBank>) currencyPrivatBankList,
                        onlineEuro -> convertToLocalDate(onlineEuro.getDate()),
                        currentPeriod);
                for (OnlineEuroPrivatBank onlineEuroPrivatBank : filteredList) {
                    onlinePurchaseSeries.addOrUpdate(new Day(onlineEuroPrivatBank.getDate()),
                            onlineEuroPrivatBank.getOnlinePurchaseEuro());
                    onlineSaleSeries.addOrUpdate(new Day(onlineEuroPrivatBank.getDate()),
                            onlineEuroPrivatBank.getOnlineSaleEuro());
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


    public TimeSeriesCollection createDatasetWithArchive(Period currentPeriod) {
        List<PrivatBankCurrencyArchive> archiveData = privatBankCurrencyBeanService.findAllArchivePB();
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        // Создаем серии для 2023 и 2024 года
        TimeSeries archiveSeries2023 = new TimeSeries("Архів ПриватБанка 2023");
        TimeSeries onlineSaleSeries = new TimeSeries("Доллар онлайн 2024");

        // Фильтрация данных архива и добавление их в соответствующую серию
        List<PrivatBankCurrencyArchive> filteredArchiveData2023 = filterByPeriod2023(
                archiveData,
                archive -> convertToLocalDate(archive.getDate()),
                currentPeriod
        );

        // Добавляем серию 2023 года в dataset
        dataset.addSeries(archiveSeries2023);
        for (PrivatBankCurrencyArchive archiveEntry : filteredArchiveData2023) {
            archiveSeries2023.addOrUpdate(new Day(archiveEntry.getDate()),
                    archiveEntry.getOnlineDollar());
            archiveSeries2023.addOrUpdate(new Day(archiveEntry.getDate()),
                    archiveEntry.getOnlineEuro());
        }

        // Определение начальной даты для архива 2024 года
        LocalDate archiveEndDate = filteredArchiveData2023.stream()
                .map(archive -> convertToLocalDate(archive.getDate()))
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now());

        // Фильтрация данных архива и добавление их в соответствующую серию для 2024 года
        List<OnlineDollarPrivatBank> dollarPrivatBankList = privatBankCurrencyBeanService.findAllOnlineDollarPB();
        List<OnlineDollarPrivatBank> filteredList = filterByPeriod2024(
                dollarPrivatBankList,
                onlineDollar -> convertToLocalDate(onlineDollar.getDate()),
                currentPeriod,
                archiveEndDate
        );
        List<OnlineDollarPrivatBank> modifiedList = new ArrayList<>(filteredList);
        // Добавляем серию 2024 года в dataset
        dataset.addSeries(onlineSaleSeries);
        for (OnlineDollarPrivatBank onlineDollarPrivatBank : modifiedList) {
            LocalDate date2024 = convertToLocalDate(onlineDollarPrivatBank.getDate());

            // Вычитаем один год из даты
            LocalDate adjustedDate = date2024.minusYears(1);

            // Создаем новую дату и добавляем точку в серию
            onlineSaleSeries.addOrUpdate(new Day(Date.from(adjustedDate.atStartOfDay(ZoneId.systemDefault()).toInstant())),
                    onlineDollarPrivatBank.getOnlineSaleDollar());
        }
        return dataset;
    }
    @Override
    public JFreeChart createChart() {
        String title = null;
        if (currencyPrivatBankList.get(0) instanceof OnlineDollarPrivatBank) {
            title = "Онлайн Доллар";
        }
        else title="Євро Онлайн";

        var dataset = createDataset();
        var min=getActualBound().get("min")-1;
        var max=getActualBound().get("max")+1;
        JFreeChart chart = new ChartBuilder().
                buildTitle(title).
                buildSubTitle("ПриватБанк").
                buildMinBound(min).
                buildMaxBound(max).
                buildDataset(dataset).
                buildPeriod(currentPeriod).
                build();
        return chart;
    }

    public JFreeChart createChartWithArchive(TimeSeriesCollection archiveDateSet) {
        String title = "Порівняння курсу з 2023 р";
        var dataset = archiveDateSet;
        var min = getActualBoundForArchive().get("min") - 1;
        var max = getActualBoundForArchive().get("max") + 1;
        JFreeChart chart = new ChartBuilder()
                .buildTitle(title)
                .buildSubTitle("ПриватБанк")
                .buildMinBound(min)
                .buildMaxBound(max)
                .buildDataset(dataset)
                .buildPeriod(currentPeriod)
                .build();
        return chart;
    }
    @Override
    public byte[] convertImageToByteArray(Period period,Class<T> entityClass) {
        this.currentPeriod=period;
        if (entityClass.equals(OnlineDollarPrivatBank.class)) {
            this.currencyPrivatBankList = (List<T>) privatBankCurrencyBeanService.
                    findAllOnlineDollarPB();
        } else if (entityClass.equals(OnlineEuroPrivatBank.class)) {
            this.currencyPrivatBankList = (List<T>) privatBankCurrencyBeanService.
                    findAllOnlineEuroPB();
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

    public byte[] convertImageToByteArrayWithArchive(Period period,Class<T> entityClass) {
        this.currentPeriod=period;
        if (entityClass.equals(PrivatBankCurrencyArchive.class)) {
            this.currencyPrivatBankList = (List<T>) privatBankCurrencyBeanService.
                    findAllArchivePB();
        }
        var dataset=createDatasetWithArchive(currentPeriod);
        JFreeChart chart= createChartWithArchive(dataset);

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
                elementsToSkip = Math.max(0, currencyPrivatBankList.size() - 10);
                break;
            case MONTH:
                elementsToSkip = Math.max(0, currencyPrivatBankList.size() - 30);
                break;
            case QUARTER:
                elementsToSkip = Math.max(0, currencyPrivatBankList.size() - 90);
                break;
            default:
                elementsToSkip = 0;
                break;
        }
        double minCurrencyBound = 0.0;
        double maxCurrencyBound = 0.0;
        Stream<Double> purchaseStream;
        Stream<Double> saleStream;

        if (!currencyPrivatBankList.isEmpty()) {
            if (currencyPrivatBankList.get(0) instanceof OnlineDollarPrivatBank) {
                purchaseStream = currencyPrivatBankList.stream()
                        .map(OnlineDollarPrivatBank.class::cast)
                        .map(OnlineDollarPrivatBank::getOnlinePurchaseDollar);
                saleStream = currencyPrivatBankList.stream()
                        .map(OnlineDollarPrivatBank.class::cast)
                        .map(OnlineDollarPrivatBank::getOnlineSaleDollar);
            } else if (currencyPrivatBankList.get(0) instanceof OnlineEuroPrivatBank) {
                purchaseStream = currencyPrivatBankList.stream()
                        .map(OnlineEuroPrivatBank.class::cast)
                        .map(OnlineEuroPrivatBank::getOnlinePurchaseEuro);
                saleStream = currencyPrivatBankList.stream()
                        .map(OnlineEuroPrivatBank.class::cast)
                        .map(OnlineEuroPrivatBank::getOnlineSaleEuro);
            }
            else {
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

    private Map<String, Double> getActualBoundForArchive() {
        Map<String, Double> map = new HashMap<>();
        int elementsToSkip;

        switch (currentPeriod) {
            case TEN_DAYS:
                elementsToSkip = Math.max(0, currencyPrivatBankList.size() - 10);
                break;
            case MONTH:
                elementsToSkip = Math.max(0, currencyPrivatBankList.size() - 30);
                break;
            case QUARTER:
                elementsToSkip = Math.max(0, currencyPrivatBankList.size() - 90);
                break;
            case YEAR:
                elementsToSkip = Math.max(0, currencyPrivatBankList.size() - 365); // Пример, можно адаптировать под фактическое количество дней в году
                break;
            default:
                elementsToSkip = 0;
                break;
        }

        double minBound = Double.NaN;
        double maxBound = Double.NaN;

        if (!currencyPrivatBankList.isEmpty()) {
            // Определяем минимальное и максимальное значение для доллара и евро
            minBound = currencyPrivatBankList.stream()
                    .skip(elementsToSkip)
                    .mapToDouble(archive -> Math.min(((PrivatBankCurrencyArchive) archive).getOnlineDollar(), ((PrivatBankCurrencyArchive) archive).getOnlineEuro()))
                    .min()
                    .orElse(Double.NaN);
            maxBound = currencyPrivatBankList.stream()
                    .mapToDouble(archive -> Math.max(((PrivatBankCurrencyArchive) archive).getOnlineDollar(), ((PrivatBankCurrencyArchive) archive).getOnlineEuro()))
                    .max()
                    .orElse(Double.NaN);
        }

        map.put("min", minBound);
        map.put("max", maxBound);
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

    private <T> List<T> filterByPeriod2023(
            List<T> data, Function<T, LocalDate> getDateFunction,
            Period currentPeriod) {
        LocalDate currentDate = LocalDate.now();

        // Определение начальной даты для архива (2023 год)
        LocalDate archiveStartDate;
        if (currentPeriod.equals(Period.TEN_DAYS)) {
            archiveStartDate = currentDate.minusDays(10).minusYears(1);
        } else if (currentPeriod.equals(Period.MONTH)) {
            archiveStartDate = currentDate.minusMonths(1).minusYears(1);
        } else if (currentPeriod.equals(Period.QUARTER)) {
            archiveStartDate = currentDate.minusMonths(3).minusYears(1);
        } else {
            archiveStartDate = currentDate.minusYears(1);
        }

        // Определение конечной даты для архива (2023 год)
        LocalDate archiveEndDate = archiveStartDate.plusDays(30);

        // Фильтрация данных из архива (2023 год)
        return data.stream()
                .filter(entry -> {
                    LocalDate entryDate = getDateFunction.apply(entry);
                    return entryDate.isAfter(archiveStartDate) && entryDate.isBefore(archiveEndDate);
                })
                .collect(Collectors.toList());
    }

    private <T> List<T> filterByPeriod2024(
            List<T> data, Function<T, LocalDate> getDateFunction,
            Period currentPeriod, LocalDate archiveEndDate) {
        LocalDate currentDate = LocalDate.now();

        // Определение начальной даты для текущего периода (2024 год)
        LocalDate startDate;
        if (currentPeriod.equals(Period.TEN_DAYS)) {
            startDate = currentDate.minusDays(10);
        } else if (currentPeriod.equals(Period.MONTH)) {
            startDate = currentDate.minusMonths(1);
        } else if (currentPeriod.equals(Period.QUARTER)) {
            startDate = currentDate.minusMonths(3);
        } else {
            startDate = currentDate;
        }

        // Определение конечной даты для текущего периода (2024 год)
        LocalDate endDate = currentDate;

        // Фильтрация данных за текущий период (2024 год)
        return data.stream()
                .filter(entry -> {
                    LocalDate entryDate = getDateFunction.apply(entry);
                    return entryDate.isAfter(startDate) && entryDate.isBefore(endDate) && entryDate.isAfter(archiveEndDate);
                })
                .collect(Collectors.toList());
    }
    private LocalDate convertToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
