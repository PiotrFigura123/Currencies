package com.IL.currencies;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.kafka.core.KafkaTemplate;


@SpringBootApplication
@EnableCaching
public class CurrenciesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrenciesApplication.class, args);
    }

}
