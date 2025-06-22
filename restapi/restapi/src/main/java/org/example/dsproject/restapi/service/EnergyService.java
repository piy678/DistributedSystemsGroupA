package org.example.dsproject.restapi.service;

import org.example.dsproject.restapi.dto.CurrentPercentageDto;
import org.example.dsproject.restapi.dto.UsageDataDto;
import org.example.dsproject.restapi.model.CurrentPercentage;
import org.example.dsproject.restapi.model.UsageData;
import org.example.dsproject.restapi.repository.CurrentPercentageRepository;
import org.example.dsproject.restapi.repository.UsageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.dsproject.restapi.model.UsageHour;
import org.example.dsproject.restapi.repository.UsageHourRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnergyService {

    private final UsageDataRepository usageDataRepository;
    private final CurrentPercentageRepository currentPercentageRepository;

    public EnergyService(UsageDataRepository usageDataRepository,
                         CurrentPercentageRepository currentPercentageRepository) {
        this.usageDataRepository = usageDataRepository;
        this.currentPercentageRepository = currentPercentageRepository;
    }

    @Autowired
    private UsageHourRepository usageHourRepository;

    public List<UsageDataDto> getHistoricalData(LocalDateTime start, LocalDateTime end) {
        List<UsageHour> usage = usageHourRepository.findAllByHourBetween(start, end);
        return usage.stream().map(u -> new UsageDataDto(
                u.getHour(),
                u.getCommunityProduced() != null ? u.getCommunityProduced() : 0.0,
                u.getCommunityUsed() != null ? u.getCommunityUsed() : 0.0,
                u.getGridUsed() != null ? u.getGridUsed() : 0.0
        )).toList();
    }



    public CurrentPercentageDto getCurrentPercentage() {
        LocalDateTime hour = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        CurrentPercentage cp = currentPercentageRepository.findByHour(hour);

        if (cp == null) {
            cp = currentPercentageRepository.findTopByOrderByHourDesc();  // Letzter bekannter
        }

        return cp != null ? toDto(cp) : null;
    }



    private UsageDataDto toDto(UsageData usage) {
        return new UsageDataDto(
                usage.getHour(),
                usage.getCommunityProduced(),
                usage.getCommunityUsed(),
                usage.getGridUsed()
        );
    }

    private CurrentPercentageDto toDto(CurrentPercentage cp) {
        return new CurrentPercentageDto(
                cp.getHour(),
                cp.getCommunityDepleted(),
                cp.getGridPortion()
        );
    }
}
