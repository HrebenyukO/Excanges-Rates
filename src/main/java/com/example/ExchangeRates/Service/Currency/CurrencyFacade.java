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
    @Autowired
    private AbankCurrencyBeanService aBankService;
    public void getActualCurrency(){
        //  nacBankService.create();
       // monoBankService.createOnlineDollar();

        //privatBankService.createOnlineDollar();
       // privatBankService.createOnlineEuro();

        // aBankService.createOnlineDollar();
        //aBankService.createOnlineEuro();
        /*try {
            Thread.sleep(61000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        monoBankService.createOnlineEuro();*/
    }
}
