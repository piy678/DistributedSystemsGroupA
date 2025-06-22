package org.example.dsproject.restapi.service;

import org.example.dsproject.restapi.dto.CurrentPercentageDto;
import org.example.dsproject.restapi.dto.UsageDataDto;
import org.example.dsproject.restapi.model.CurrentPercentage;
import org.example.dsproject.restapi.model.UsageData;
import org.example.dsproject.restapi.repository.CurrentPercentageRepository;
import org.example.dsproject.restapi.repository.UsageDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EnergyService {

    private final UsageDataRepository usageDataRepository;
    private final CurrentPercentageRepository currentPercentageRepository;

    public EnergyService(UsageDataRepository usageDataRepository,
                         CurrentPercentageRepository currentPercentageRepository) {
        this.usageDataRepository = usageDataRepository;
        this.currentPercentageRepository = currentPercentageRepository;
    }

    public List<UsageDataDto> getHistoricalData(LocalDateTime start, LocalDateTime end) {
        List<UsageData> data = usageDataRepository.findAllByHourBetween(start, end);
        return data.stream().map(this::toDto).toList();
    }

    public CurrentPercentageDto getCurrentPercentage() {
        LocalDateTime hour = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        CurrentPercentage cp = currentPercentageRepository.findByHour(hour);
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
