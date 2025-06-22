package org.example.dsproject.restapi.api;

import org.example.dsproject.restapi.dto.CurrentPercentageDto;
import org.example.dsproject.restapi.repository.CurrentPercentageRepository;
import org.example.dsproject.restapi.repository.UsageDataRepository;
import org.example.dsproject.restapi.service.EnergyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnergyControllerTest {

    private EnergyController controller;
    private EnergyService energyService;
    private UsageDataRepository usageRepo;
    private CurrentPercentageRepository percentageRepo;

    @BeforeEach
    void setup() {
        energyService = mock(EnergyService.class);
        usageRepo = mock(UsageDataRepository.class);
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
}
