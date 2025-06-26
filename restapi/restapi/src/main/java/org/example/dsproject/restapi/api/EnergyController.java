package org.example.dsproject.restapi.api;

import lombok.Getter;
import org.example.dsproject.restapi.dto.CurrentPercentageDto;
import org.example.dsproject.restapi.dto.UsageHourDto;
import org.example.dsproject.restapi.model.CurrentPercentage;
import org.example.dsproject.restapi.model.UsageHour;
import org.example.dsproject.restapi.repository.CurrentPercentageRepository;
import org.example.dsproject.restapi.repository.UsageHourRepository;
import org.example.dsproject.restapi.service.EnergyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EnergyController {

    private final EnergyService energyService;

    private final CurrentPercentageRepository percentageRepo;

    private final UsageHourRepository usageRepo;

    public EnergyController(EnergyService energyService,
                            UsageHourRepository usageRepo,
                            CurrentPercentageRepository percentageRepo) {
        this.energyService = energyService;
        this.usageRepo = usageRepo;
        this.percentageRepo = percentageRepo;
    }


    @GetMapping("/percentage/current")
    public ResponseEntity<CurrentPercentageDto> getCurrentPercentage() {
        try {
            CurrentPercentageDto dto = energyService.getCurrentPercentage();
            if (dto == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/usage")
    public ResponseEntity<List<UsageHourDto>> getHistoricalData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<UsageHourDto> data = energyService.getHistoricalData(start, end);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/summary")
    public List<EnergySummaryDto> getSummary(@RequestParam(defaultValue = "24") int hours) {
        LocalDateTime end = LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.HOURS);// Lokale Zeit
        LocalDateTime start = end.minusHours(hours);

        List<UsageHour> usage = usageRepo.findAllByHourBetween(start, end);

        List<CurrentPercentage> percent = percentageRepo.findAllByHourBetween(start, end);

        Map<LocalDateTime, CurrentPercentage> percentMap = percent.stream()
                .collect(Collectors.toMap(CurrentPercentage::getHour, p -> p));

        return usage.stream().map(u -> {
            CurrentPercentage p = percentMap.getOrDefault(u.getHour(), new CurrentPercentage());
            return new EnergySummaryDto(
                    u.getHour().toString(),
                    u.getCommunityProduced() != null ? u.getCommunityProduced() : 0.0,
                    u.getCommunityUsed() != null ? u.getCommunityUsed() : 0.0,
                    u.getGridUsed() != null ? u.getGridUsed() : 0.0,
                    p.getCommunityDepleted() != null ? p.getCommunityDepleted() : 0.0,
                    p.getGridPortion() != null ? p.getGridPortion() : 0.0
            );
        }).toList();

    }


    // DTO für die Zusammenfassung
    @Getter
    public static class EnergySummaryDto {
        private String hour;
        private double produced;
        private double used;
        private double gridUsed;
        private double communityDepleted;
        private double gridPortion;

        public EnergySummaryDto(String hour, double produced, double used, double gridUsed, double communityDepleted, double gridPortion) {
            this.hour = hour;
            this.produced = produced;
            this.used = used;
            this.gridUsed = gridUsed;
            this.communityDepleted = communityDepleted;
            this.gridPortion = gridPortion;
        }

    }
}
