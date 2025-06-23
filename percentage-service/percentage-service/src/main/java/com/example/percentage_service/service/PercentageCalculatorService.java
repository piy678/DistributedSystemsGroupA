package com.example.percentage_service.service;

import com.example.percentage_service.model.CurrentPercentage;
import com.example.percentage_service.model.UsageHour;
import com.example.percentage_service.repository.CurrentPercentageRepository;
import com.example.percentage_service.repository.UsageHourRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class PercentageCalculatorService {

    private final UsageHourRepository usageHourRepository;
    private final CurrentPercentageRepository currentPercentageRepository;

    public PercentageCalculatorService(
            UsageHourRepository usageHourRepository,
            CurrentPercentageRepository currentPercentageRepository
    ) {
        this.usageHourRepository = usageHourRepository;
        this.currentPercentageRepository = currentPercentageRepository;
    }

    public void calculateForHour(LocalDateTime hour) {
        Optional<UsageHour> usageOpt = usageHourRepository.findById(hour);

        if (usageOpt.isEmpty()) {
            log.warn("Keine Verbrauchsdaten für Stunde: {}", hour);
            return;
        }

        CurrentPercentage result = calculatePercentage(usageOpt.get(), hour);
        currentPercentageRepository.save(result);

        log.info("Prozentwerte gespeichert für {}: Community={}%, Grid={}%",
                hour, result.getCommunityDepleted(), result.getGridPortion());
    }

    private CurrentPercentage calculatePercentage(UsageHour usage, LocalDateTime hour) {
        double produced = usage.getCommunityProduced();
        double used = usage.getCommunityUsed();
        double grid = usage.getGridUsed();
        double totalUsed = used + grid;

        double communityDepleted = (produced == 0)
                ? 100.0
                : Math.min(100.0, (used / produced) * 100.0);
        communityDepleted = Math.round(communityDepleted * 1000.0) / 1000.0;

        double gridPortion = (totalUsed == 0)
                ? 0.0
                : (grid / totalUsed) * 100.0;
        gridPortion = Math.round(gridPortion * 10000.0) / 10000.0;

        return new CurrentPercentage(hour, communityDepleted, gridPortion);
    }

}
