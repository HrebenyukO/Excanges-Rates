package com.example.ExchangeRates.Service.Currency;


import com.example.ExchangeRates.Service.SendTable.CreateTable;
import com.example.ExchangeRates.dto.CurrencyOnlineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    UkrGazBankCurrencyBeanService ukrGazBankService;

    private HashMap<String, CurrencyOnlineDTO> lastCurrencyHashMap;




    public void getAndSaveActualCurrency() {

       /* sensBankService.createOrUpdateOnlineDollar();
        sensBankService.createOrUpdateOnlineEuro();
         nacBankService.create();
        monoBankService.createOrUpdateOnlineDollar();
        privatBankService.createOrUpdateOnlineDollar();
        privatBankService.createOrUpdateOnlineEuro();*/
        privatBankService.createOrUpdateCashDollar();
        privatBankService.createOrUpdateCashEuro();
        /*aBankService.createOrUpdateOnlineDollar();
        aBankService.createOrUpdateOnlineEuro();
        ukrGazBankService.createOrUpdateOnlineDollar();
        ukrGazBankService.createOrUpdateOnlineEuro();
        try {
            Thread.sleep(61000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        monoBankService.createOrUpdateOnlineEuro();
        try {
            Thread.sleep(361000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }

    public byte[] createTable(){
        return createTable.createCurrencyTableBytes();
    }

}