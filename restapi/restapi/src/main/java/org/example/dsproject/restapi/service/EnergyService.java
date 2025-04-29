package org.example.dsproject.restapi.service;


import org.example.dsproject.restapi.model.CurrentPercentage;
import org.example.dsproject.restapi.model.UsageData;
import org.example.dsproject.restapi.repository.CurrentPercentageRepository;
import org.example.dsproject.restapi.repository.UsageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnergyService {

    public CurrentPercentage getCurrentPercentage() {
        return new CurrentPercentage(1L, 45.5, 54.5, LocalDateTime.now());
    }

    public List<UsageData> getHistoricalData(String start, String end) {
        return List.of(
                new UsageData(1L, 100.0, 90.0, 10.0, LocalDateTime.now().minusHours(2)),
                new UsageData(2L, 110.0, 85.0, 25.0, LocalDateTime.now().minusHours(1)),
                new UsageData(3L, 120.0, 100.0, 20.0, LocalDateTime.now())
        );
    }
}
