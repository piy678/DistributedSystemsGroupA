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

    private final CurrentPercentageRepository currentRepo;
    private final UsageDataRepository usageRepo;

    @Autowired
    public EnergyService(CurrentPercentageRepository currentRepo, UsageDataRepository usageRepo) {
        this.currentRepo = currentRepo;
        this.usageRepo = usageRepo;
    }

    public CurrentPercentage getCurrentPercentage() {
        return currentRepo.findTopByOrderByHourDesc();
    }

    public List<UsageData> getHistoricalData(String start, String end) {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        return usageRepo.findByHourBetween(startTime, endTime);
    }
}
