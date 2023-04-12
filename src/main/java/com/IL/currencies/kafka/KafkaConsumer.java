package com.IL.currencies.kafka;


import com.IL.currencies.dto.ExchangeDto;
import com.IL.currencies.model.Exchange;
import com.IL.currencies.repository.ExchangeRepository;
import com.IL.currencies.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {

    private final ExchangeRepository exchangeRepository;
    private final ExchangeService exchangeService;
    @Autowired
    private RedisTemplate<String, String> template;
    private static final String STRING_KEY_PREFIX = "exchanges";

    @Cacheable("exchanges")
    @KafkaListener(topics = "apiRequest", groupId = "myGroup")
    public void consume(ExchangeDto source) {
        log.info(String.format("KafkaConsumer -> %s", source));
        Exchange exchange = exchangeService.convertFromDtoToEntity(source);
        exchangeRepository.save(exchange);
        template.opsForValue().set(STRING_KEY_PREFIX, exchange.toString());
    }
}
