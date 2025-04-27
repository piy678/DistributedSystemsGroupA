package org.example.dsproject.restapi.api;


import org.example.dsproject.restapi.model.CurrentPercentage;
import org.example.dsproject.restapi.model.UsageData;
import org.example.dsproject.restapi.service.EnergyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/energy")
@CrossOrigin
public class EnergyController {

    private final EnergyService energyService;

    public EnergyController(EnergyService energyService) {
        this.energyService = energyService;
    }

    @GetMapping("/current")
    public CurrentPercentage getCurrentPercentage() {
        return energyService.getCurrentPercentage();
    }

    @GetMapping("/historical")
    public List<UsageData> getHistoricalData(@RequestParam String start, @RequestParam String end) {
        return energyService.getHistoricalData(start, end);
    }
}
