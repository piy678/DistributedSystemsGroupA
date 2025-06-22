package org.example.energyuser;

import org.example.energyuser.EnergyStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    private final EnergyStorage storage;

    public EnergyController(EnergyStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/total")
    public double getTotal() {
        return storage.getTotal();
    }
}
