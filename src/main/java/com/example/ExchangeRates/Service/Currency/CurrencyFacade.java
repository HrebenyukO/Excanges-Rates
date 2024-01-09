package com.example.ExchangeRates.Service.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyFacade {
    @Autowired
    private MonoBankCurrencyBeanService monoBankService;

    @Autowired
    private NacBankCurrencyBeanService nacBankService;

    @Autowired
    private PrivatBankCurrencyBeanService privatBankService;

    public void getActualCurrency(){
        nacBankService.create();
        monoBankService.createOnlineDollar();
        monoBankService.createOnlineEuro();
        privatBankService.createOnlineDollar();
        privatBankService.createOnlineEuro();
    }
}
