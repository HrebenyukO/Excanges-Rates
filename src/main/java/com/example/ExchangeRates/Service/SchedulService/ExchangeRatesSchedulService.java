package com.example.ExchangeRates.Service.SchedulService;

//import com.example.ExchangeRates.Mappers.EntityMapper.EntityMapper;
import com.example.ExchangeRates.Service.Currency.NacBankCurrencyBeanService;
        import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExchangeRatesSchedulService {

   private final String PRIVATBANK="PrivatBank";
    @Autowired
    NacBankCurrencyBeanService nacBankCurrencyBeanService;

    @Scheduled(cron = "0 0 0 * * *")
    public void saveActualExchangeRates(){
    nacBankCurrencyBeanService.create();
        log.info("Save currency NacBank");
    }


}
