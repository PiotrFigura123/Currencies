package com.IL.currencies.repository;

import com.IL.currencies.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlExchangeRepo extends ExchangeRepository, JpaRepository<Exchange, Integer> {

    @Override
    List<Exchange> findAll();

    @Override
    boolean existsByVersionId(Integer versionId);

    @Override
    List<Exchange> findAllByVersionId(Integer versionId);

    @Override
    Exchange save(Exchange entity);
}
