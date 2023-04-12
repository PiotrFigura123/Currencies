package com.IL.currencies.kafka;

import com.IL.currencies.dto.ExchangeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<Object, ExchangeDto> kafkaTemplate;


    public KafkaProducer(final KafkaTemplate<Object, ExchangeDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ExchangeDto source) {

        log.info(String.format("KafkaProducer, messageSent %s", source));
        kafkaTemplate.send("apiRequest", source);
    }
}
