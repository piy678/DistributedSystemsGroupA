package org.example.dsproject.restapi.api;

import org.example.dsproject.restapi.dto.CurrentPercentageDto;
import org.example.dsproject.restapi.model.CurrentPercentage;
import org.example.dsproject.restapi.model.UsageHour;
import org.example.dsproject.restapi.repository.CurrentPercentageRepository;
import org.example.dsproject.restapi.repository.UsageHourRepository;
import org.example.dsproject.restapi.service.EnergyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnergyControllerTest {

    private EnergyController controller;
    private EnergyService energyService;
    private UsageHourRepository usageRepo;
    private CurrentPercentageRepository percentageRepo;

    @BeforeEach
    void setup() {
        energyService = mock(EnergyService.class);
        usageRepo = mock(UsageHourRepository.class);
        percentageRepo = mock(CurrentPercentageRepository.class);
        controller = new EnergyController(energyService, usageRepo, percentageRepo);
    }

    @Test
    void testGetCurrentPercentage_notFound() {
        when(energyService.getCurrentPercentage()).thenReturn(null);

        ResponseEntity<CurrentPercentageDto> response = controller.getCurrentPercentage();

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetCurrentPercentage_internalError() {
        when(energyService.getCurrentPercentage()).thenThrow(new RuntimeException("Boom"));

        ResponseEntity<CurrentPercentageDto> response = controller.getCurrentPercentage();

        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetSummary_returnsEmptyList() {
        when(usageRepo.findAllByHourBetween(any(), any())).thenReturn(Collections.emptyList());
        when(percentageRepo.findAllByHourBetween(any(), any())).thenReturn(Collections.emptyList());

        List<EnergyController.EnergySummaryDto> result = controller.getSummary(24);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetSummary_withData() {
        LocalDateTime hour = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        UsageHour usage = new UsageHour();
        usage.setHour(hour);
        usage.setCommunityProduced(100.0);
        usage.setCommunityUsed(80.0);
        usage.setGridUsed(20.0);

        CurrentPercentage percentage = new CurrentPercentage(hour, 80.0, 20.0);

        when(usageRepo.findAllByHourBetween(any(), any())).thenReturn(List.of(usage));
        when(percentageRepo.findAllByHourBetween(any(), any())).thenReturn(List.of(percentage));

        List<EnergyController.EnergySummaryDto> result = controller.getSummary(1);

        assertEquals(1, result.size());
        EnergyController.EnergySummaryDto dto = result.get(0);
        assertEquals(hour.toString(), dto.getHour());
        assertEquals(100.0, dto.getProduced());
        assertEquals(80.0, dto.getUsed());
        assertEquals(20.0, dto.getGridUsed());
        assertEquals(80.0, dto.getCommunityDepleted());
        assertEquals(20.0, dto.getGridPortion());
    }
}
