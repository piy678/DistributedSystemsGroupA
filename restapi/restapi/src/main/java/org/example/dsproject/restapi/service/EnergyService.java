package org.example.dsproject.restapi.service;

import org.example.dsproject.restapi.dto.CurrentPercentageDto;
import org.example.dsproject.restapi.dto.UsageHourDto;
import org.example.dsproject.restapi.model.CurrentPercentage;
//import org.example.dsproject.restapi.model.UsageData;
import org.example.dsproject.restapi.repository.CurrentPercentageRepository;
//import org.example.dsproject.restapi.repository.UsageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.dsproject.restapi.model.UsageHour;
import org.example.dsproject.restapi.repository.UsageHourRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EnergyService {

    //private final UsageHourRepository usageDataRepository;
    private final CurrentPercentageRepository currentPercentageRepository;
    @Autowired
    private UsageHourRepository usageHourRepository;

    public EnergyService(UsageHourRepository usageDataRepository,
                         CurrentPercentageRepository currentPercentageRepository) {
        this.usageHourRepository = usageDataRepository;
        this.currentPercentageRepository = currentPercentageRepository;
    }



    public List<UsageHourDto> getHistoricalData(LocalDateTime start, LocalDateTime end) {
        List<UsageHour> usage = usageHourRepository.findAllByHourBetween(start, end);
        return usage.stream().map(u -> new UsageHourDto(
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



    private UsageHourDto toDto(UsageHour usage) {
        return new UsageHourDto(
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
