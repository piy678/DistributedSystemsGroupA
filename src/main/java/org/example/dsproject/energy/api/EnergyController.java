package org.example.dsproject.energy.api;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {


    public static class EnergyData {
        private LocalDateTime hour;
        private double communityDepleted;
        private double gridPortion;

        public EnergyData(LocalDateTime hour, double communityDepleted, double gridPortion) {
            this.hour = hour;
            this.communityDepleted = communityDepleted;
            this.gridPortion = gridPortion;
        }


        public LocalDateTime getHour() {
            return hour;
        }

        public double getCommunityDepleted() {
            return communityDepleted;
        }

        public double getGridPortion() {
            return gridPortion;
        }
    }

   
    @GetMapping("/current")
    public EnergyData getCurrentEnergy() {
        LocalDateTime currentHour = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        return new EnergyData(currentHour, 100.0, 5.63);
    }


    @GetMapping("/historical")
    public List<EnergyData> getHistoricalData(
            @RequestParam String start,
            @RequestParam String end) {

        List<EnergyData> dataList = new ArrayList<>();


        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);

        while (!startTime.isAfter(endTime)) {
            double simulatedCommunity = Math.random() * 100;
            double simulatedGrid = Math.random() * 10;
            dataList.add(new EnergyData(startTime, simulatedCommunity, simulatedGrid));
            startTime = startTime.plusHours(1);
        }

        return dataList;
    }
}
