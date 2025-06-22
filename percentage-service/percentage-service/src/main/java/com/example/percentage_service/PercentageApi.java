package com.example.percentage_service;

import com.example.percentage_service.model.CurrentPercentage;
import com.example.percentage_service.repository.CurrentPercentageRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/percentage")
public class PercentageApi {

    private final CurrentPercentageRepository repo;

    public PercentageApi(CurrentPercentageRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/current")
    public CurrentPercentage getCurrentPercentage(@RequestParam(required = false) String hour) {
        if (hour != null) {
            LocalDateTime time = LocalDateTime.parse(hour);
            return repo.findByHour(time).orElse(null);
        }

        // Aktuelle volle Stunde
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        // Falls kein Eintrag für jetzt, nimm den neuesten verfügbaren
        return repo.findByHour(now)
                .orElseGet(() -> repo.findTopByOrderByHourDesc().orElse(null));
    }
}
