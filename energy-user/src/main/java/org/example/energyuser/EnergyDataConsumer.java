package org.example.energyuser;

import org.example.energyuser.EnergyStorage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EnergyDataConsumer {

    private final EnergyStorage storage;

    public EnergyDataConsumer(EnergyStorage storage) {
        this.storage = storage;
    }

    @RabbitListener(queues = "energy-data")
    public void consumeEnergyData(EnergyMessage message) {
        storage.add(message);
        System.out.println("Empfangen: " + message);
    }
}
