package com.IL.currencies.repository;

import com.IL.currencies.model.Currency;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface CurrencyRepository {

    List<Currency> findAll();

    Optional<Currency>  findByCurrencyId(Integer id);

}
