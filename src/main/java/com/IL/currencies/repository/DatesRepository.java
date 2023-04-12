package com.IL.currencies.repository;

import com.IL.currencies.model.DatesEntity;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DatesRepository {

    List<DatesEntity> findAll();

    boolean existsByDateOfUpdate(@Param("date") String date);

    DatesEntity save(DatesEntity entity);

    Optional<String> findByDateId(@Param("date_id")Integer dateId);

    Integer getTheHighestId();
}
