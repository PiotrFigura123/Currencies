package com.IL.currencies.service;

import com.IL.currencies.model.DatesEntity;
import com.IL.currencies.repository.DatesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatesServiceTest {


    @Mock
    DatesRepository datesRepository;

    @InjectMocks
    DatesService datesService;

    @Test
    public void shouldNotSaveDateInDbAndReturnLastIdFromDb() {

        given(datesRepository.existsByDateOfUpdate(any())).willReturn(true);
        given(datesRepository.getTheHighestId()).willReturn(2);

        assertThat(datesService.addTodayDayToTable(), is(2));

    }

    @Test
    public void shouldSaveNewDateInDB() {
        // given

        LocalDateTime today = LocalDateTime.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("yyy-MM-dd"));

        DatesEntity datesEntity = new DatesEntity();
        when(datesRepository.existsByDateOfUpdate(formattedToday)).thenReturn(false);
        when(datesRepository.save(any())).thenReturn(datesEntity);

        // when
        datesService.addTodayDayToTable();

        // then

        verify(datesRepository, times(1)).existsByDateOfUpdate(formattedToday);
        verify(datesRepository, times(1)).save(any(DatesEntity.class));
        verify(datesRepository).getTheHighestId();
    }

    @Test
    public void shouldNotSaveNewDateInDB() {
        // given
        LocalDateTime today = LocalDateTime.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("yyy-MM-dd"));
        when(datesRepository.existsByDateOfUpdate(formattedToday)).thenReturn(true);

        // when
        datesService.addTodayDayToTable();

        // then

        verify(datesRepository, times(1)).existsByDateOfUpdate(formattedToday);
        verify(datesRepository).getTheHighestId();
    }

    @Test
    public void shouldReturnExpectedDate() {
        // given

        String expectedDate = "2022-04-12";
        when(datesRepository.findByDateId(1)).thenReturn(Optional.of(expectedDate));

        // when
        String actualDate = datesService.findDateByVersionId(1);

        // then
        assertEquals(expectedDate, actualDate);
        verify(datesRepository, times(1)).findByDateId(1);
        verifyNoMoreInteractions(datesRepository);
    }

    @Test
    public void shouldThrowExceptionWhenNoDataInRepo() {
        // given

        when(datesRepository.findByDateId(1)).thenReturn(Optional.empty());

        // when / then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> datesService.findDateByVersionId(1));
        assertEquals("no version in DB", exception.getMessage());
        verify(datesRepository, times(1)).findByDateId(1);
        verifyNoMoreInteractions(datesRepository);
    }

}


