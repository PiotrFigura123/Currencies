package com.IL.currencies.service;


import com.IL.currencies.dto.ExchangeDto;
import com.IL.currencies.dto.ExchangeReadModel;
import com.IL.currencies.kafka.KafkaProducer;
import com.IL.currencies.model.Currency;
import com.IL.currencies.model.Exchange;
import com.IL.currencies.repository.CurrencyRepository;
import com.IL.currencies.repository.ExchangeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExchangeService {

    private final CurrencyRepository currencyRepository;
    private final APIConnection serviceAPI;
    private final KafkaProducer kafkaProducer;
    private final ExchangeRepository exchangeRepository;
    private final DatesService datesService;


    public void getDataFromApiToKafkaProducer() {

        int versionId = datesService.addTodayDayToTable();
        if (exchangeRepository.existsByVersionId(versionId)) {
            exchangeRepository.findAllByVersionId(versionId);
            log.info("NIE dla kafki");
            return;
        }
        JsonNode rootNode = serviceAPI.getDataFromServer();
        createExchangeDto(rootNode);

    }

    private void createExchangeDto(JsonNode rootNode) {

        for (Currency rateCurr : currencyRepository.findAll()) {
            ExchangeDto exchangeDto = setExchangeDTO(rootNode, rateCurr);
            kafkaProducer.sendMessage(exchangeDto);
            log.info("wysylam do kafki");
        }
    }



    public Exchange convertFromDtoToEntity(final ExchangeDto source) {
        return new Exchange(source.getVersionId(), source.getBaseCurrency()
                , source.getRateCurrency(), convertRateValueFromStringToFloat(source.getRateValue()));
    }

    public List<ExchangeReadModel> loadExchangeData() {

        if (checkIfDateExistInDatabase()) {
            List<ExchangeReadModel> readModelList = new ArrayList<>();
            List<Exchange> exchanges = exchangeRepository.findAllByVersionId(datesService.getCurrentVersionId());
            for (Exchange exchange : exchanges) {
                ExchangeReadModel exchangeReadModel = convertExchangeToExchangeReadModel(exchange);
                readModelList.add(exchangeReadModel);
            }
            return readModelList;
        }
        return null;
    }

    private ExchangeDto setExchangeDTO(final JsonNode rootNode, final Currency rateCurr) {
        ExchangeDto exchangeDto = new ExchangeDto();
        exchangeDto.setRateCurrency(rateCurr.getCurrencyId());
        exchangeDto.setRateValue(rootNode.get("rates").get(rateCurr.getBase()).toString());
        exchangeDto.setVersionId(datesService.getVersionId());
        return exchangeDto;
    }

    private ExchangeReadModel convertExchangeToExchangeReadModel(final Exchange exchange) {
            try {
                Optional<Currency> rateCurrency = currencyRepository.findByCurrencyId(exchange.getRateCurrency());
                Currency currency = rateCurrency.orElseThrow(IllegalAccessError::new);
                String rateCurrencyName = currency.getBase();
                return new ExchangeReadModel("USD", rateCurrencyName, exchange.getRateValue());
            } catch (NullPointerException e) {
                log.info("source exchange.getRateCurrency() = " + exchange.getRateCurrency());
            }

        return null;
    }

    private Float convertRateValueFromStringToFloat(final String rateValue) {
        Pattern pattern = Pattern.compile("\\d\\.\\d{2}");
        Matcher matcher = pattern.matcher(rateValue);
        try {
            if (matcher.find()) {
                String match = matcher.group();
                return Float.parseFloat(match);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return 1.0f;
    }
    public boolean checkIfDateExistInDatabase(){
        String date = datesService.findDateByVersionId(datesService.getCurrentVersionId());
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
        return today.format(formatter).equals(date);
    }


}
