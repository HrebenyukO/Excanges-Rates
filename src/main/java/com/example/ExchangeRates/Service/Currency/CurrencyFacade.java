package com.example.ExchangeRates.Service.Currency;


import com.example.ExchangeRates.Entity.Currency.OnlineDollar.OnlineDollarMonobank;
import com.example.ExchangeRates.Entity.Currency.OnlineEuro.OnlineEuroMonoBank;
import com.example.ExchangeRates.Service.SendTable.CreateTable;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@Service
public class CurrencyFacade {
    @Autowired
    private MonoBankCurrencyBeanService monoBankService;
    @Autowired
    private NacBankCurrencyBeanService nacBankService;
    @Autowired
    private PrivatBankCurrencyBeanService privatBankService;
    @Autowired
    private AbankCurrencyBeanService aBankService;
    @Autowired
    private SensBankCurrencyBeanService sensBankService;
    @Autowired
    CreateTable createTable;


    private HashMap<String, CurrencyOnlineDTO> lastCurrencyHashMap;




    public void getAndSaveActualCurrency() {
       /*nacBankService.create();
        monoBankService.createAndSaveOnlineDollar();
        privatBankService.createOnlineDollar();
        privatBankService.createOnlineEuro();*/
        aBankService.createOnlineDollar();
       // aBankService.createOnlineEuro();
        try {
            Thread.sleep(61000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        monoBankService.createAndSaveOnlineEuro();
        try {
            Thread.sleep(361000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] createTable(){
        return createTable.createCurrencyTableBytes();
    }

}