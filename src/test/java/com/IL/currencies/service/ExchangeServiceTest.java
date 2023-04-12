package com.IL.currencies.service;


import com.IL.currencies.repository.ExchangeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {


    @Mock
    DatesService datesService;
    @Mock
    ExchangeRepository exchangeRepository;
    @Mock
    APIConnection serviceAPI;

    @InjectMocks
    ExchangeService exchangeService;

    @Test
    public void shouldNotSendDataToServiceApi() {

        given(exchangeRepository.existsByVersionId(anyInt())).willReturn(true);
        exchangeService.getDataFromApiToKafkaProducer();
        //then
        verify(exchangeRepository).findAllByVersionId(anyInt());
        verifyNoMoreInteractions(serviceAPI);
        verifyNoMoreInteractions(exchangeRepository);
    }


    @Test
    public void shouldThrowAnExceptionDuringConnectingToAPIService() {
       //when
        int versionId = 1;
        given(datesService.addTodayDayToTable()).willReturn(versionId);
        given(exchangeRepository.existsByVersionId(versionId)).willReturn(false);
        RuntimeException exception = new RuntimeException();
        when(serviceAPI.getDataFromServer()).thenThrow(exception);

        // then
        assertThrows(RuntimeException.class, () -> exchangeService.getDataFromApiToKafkaProducer());
        verify(exchangeRepository).existsByVersionId(versionId);
        verifyNoMoreInteractions(exchangeRepository);
        verify(serviceAPI).getDataFromServer();
        verifyNoMoreInteractions(serviceAPI);
    }


}