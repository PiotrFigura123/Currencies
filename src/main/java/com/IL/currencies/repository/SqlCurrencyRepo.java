package com.IL.currencies.repository;

import com.IL.currencies.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface SqlCurrencyRepo extends CurrencyRepository, JpaRepository<Currency, Integer> {

    @Override
    List<Currency> findAll();

    @Override
    Optional<Currency> findByCurrencyId(Integer id);


}
