package org.example.energyuser;

import org.example.energyuser.EnergyMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EnergyStorage {
    private final List<EnergyMessage> messages = new ArrayList<>();

    public void add(EnergyMessage message) {
        messages.add(message);
    }

    public double getTotal() {
        return messages.stream().mapToDouble(EnergyMessage::getKwh).sum();
    }

    public List<EnergyMessage> getAll() {
        return messages;
    }
}
