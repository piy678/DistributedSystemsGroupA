/*package org.example.dsproject.restapi.demo;

import jakarta.annotation.PostConstruct;
import org.example.dsproject.restapi.model.CurrentPercentage;
import org.example.dsproject.restapi.model.UsageData;
import org.example.dsproject.restapi.repository.CurrentPercentageRepository;
import org.example.dsproject.restapi.repository.UsageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class Demo {
    final
    UsageDataRepository usageRepo;
    final
    CurrentPercentageRepository percentageRepo;

    public Demo(UsageDataRepository usageRepo, CurrentPercentageRepository percentageRepo) {
        this.usageRepo = usageRepo;
        this.percentageRepo = percentageRepo;
    }

    @PostConstruct
    public void init() {
        LocalDateTime base = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

        for (int i = 0; i < 5; i++) {
            LocalDateTime time = base.minusHours(i);
            usageRepo.save(new UsageData(time, 100 + i, 80 + i, 20 + i));
            percentageRepo.save(new CurrentPercentage(time, 100 - i, 10 + i));
        }
    }


}
*/