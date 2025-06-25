package com.example.usage_service;

import com.example.usage_service.messaging.UsageMessageListener;
import com.example.usage_service.model.EnergyMessage;
import com.example.usage_service.service.UsageAggregatorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.*;

class UsageMessageListenerTest {

    private UsageAggregatorService usageAggregatorService;
    private UsageMessageListener listener;

    @BeforeEach
    void setUp() {
        usageAggregatorService = mock(UsageAggregatorService.class);
        listener = new UsageMessageListener(usageAggregatorService);
    }

    @Test
    void testHandleMessage_callsService() {
        // Arrange
        EnergyMessage message = new EnergyMessage();
        message.setType("USER");
        message.setAssociation("COMMUNITY");
        message.setKwh(0.1);
        message.setDatetime(ZonedDateTime.parse("2025-06-20T15:12:00Z").toLocalDateTime());


        // Act
        listener.handleMessage(message);

        // Assert
        verify(usageAggregatorService).processMessage(message);
    }
}
