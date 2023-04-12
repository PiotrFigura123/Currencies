package com.IL.currencies.service;

import com.IL.currencies.model.DatesEntity;
import com.IL.currencies.repository.DatesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class DatesService {

    private DatesRepository datesRepository;

    DatesService(final DatesRepository datesRepository) {
        this.datesRepository = datesRepository;
    }

    LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");

    public int addTodayDayToTable() {
        DatesEntity datesEntity = new DatesEntity();
        if (!datesRepository.existsByDateOfUpdate(today.format(formatter))) {
            datesEntity.setDateOfUpdate(today.format(formatter));
            datesRepository.save(datesEntity);

        }
        log.info("Today date exist in db");
        return datesRepository.getTheHighestId();
    }

    public String findDateByVersionId(final Integer versionId) {
        return datesRepository.findByDateId(versionId).orElseThrow(() -> new IllegalArgumentException("no version in DB"));
    }

    public int getVersionId() {
        return datesRepository.findAll().stream().mapToInt(DatesEntity::getDateId).max().orElse(1);
    }

    int getCurrentVersionId() {
        return datesRepository.getTheHighestId();
    }

}
