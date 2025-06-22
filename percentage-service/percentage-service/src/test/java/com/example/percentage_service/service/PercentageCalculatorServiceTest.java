package com.example.percentage_service.service;

import com.example.percentage_service.model.CurrentPercentage;
import com.example.percentage_service.model.UsageHour;
import com.example.percentage_service.repository.CurrentPercentageRepository;
import com.example.percentage_service.repository.UsageHourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PercentageCalculatorServiceTest {

    private UsageHourRepository usageHourRepository;
    private CurrentPercentageRepository currentPercentageRepository;
    private PercentageCalculatorService service;

    @BeforeEach
    void setUp() {
        usageHourRepository = mock(UsageHourRepository.class);
        currentPercentageRepository = mock(CurrentPercentageRepository.class);
        service = new PercentageCalculatorService(usageHourRepository, currentPercentageRepository);
    }

    @Test
    void calculateForHour_shouldSaveCorrectPercentage() {
        LocalDateTime hour = LocalDateTime.of(2025, 6, 22, 14, 0);

        UsageHour usage = new UsageHour(hour, 10.0, 8.0, 2.0); // 10 produziert, 8 verbraucht, 2 vom Grid

        when(usageHourRepository.findById(hour)).thenReturn(Optional.of(usage));

        service.calculateForHour(hour);

        verify(currentPercentageRepository).save(argThat(cp -> {
            assertEquals(hour, cp.getHour());
            assertEquals(80.0, cp.getCommunityDepleted());  // 8 / 10 * 100
            assertEquals(20.0, cp.getGridPortion());        // 2 / (8+2) * 100
            return true;
        }));
    }

    @Test
    void calculateForHour_shouldNotSaveIfUsageMissing() {
        LocalDateTime hour = LocalDateTime.of(2025, 6, 22, 13, 0);
        when(usageHourRepository.findById(hour)).thenReturn(Optional.empty());

        service.calculateForHour(hour);

        verifyNoInteractions(currentPercentageRepository);
    }
}
