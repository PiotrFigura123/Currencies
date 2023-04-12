package com.IL.currencies.repository;

import com.IL.currencies.model.DatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
interface SqlDatesRepository extends DatesRepository, JpaRepository<DatesEntity, Integer> {
    @Override
    List<DatesEntity> findAll();

    @Override
    @Query(nativeQuery = true, value = "SELECT date_of_update from dates where date_id=:date_id")
    Optional<String> findByDateId(@Param("date_id")Integer dateId);

    @Override
    @Query(nativeQuery = true, value = "select count(*)>0 from dates where date_of_update=:date")
    boolean existsByDateOfUpdate(@Param("date") String date);

    @Override
    @Query(nativeQuery = true, value = "SELECT MAX(date_id) from dates")
    Integer getTheHighestId();

    @Override
    DatesEntity save(DatesEntity entity);
}
