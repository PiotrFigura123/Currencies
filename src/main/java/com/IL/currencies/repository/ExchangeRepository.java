package com.IL.currencies.repository;

import com.IL.currencies.model.Exchange;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository {

    List<Exchange> findAllByVersionId(Integer versionId);

    Exchange save(Exchange entity);

    boolean existsByVersionId(Integer versionId);


}
