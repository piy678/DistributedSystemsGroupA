package org.example.energyproducer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class EnergyGenerator {

    private final EnergyProducerSender sender;
    private final Random random = new Random();

    public EnergyGenerator(EnergyProducerSender sender) {
        this.sender = sender;
    }

    @Scheduled(fixedDelay = 3000) // Alle 3 Sekunden
    public void generateEnergy() {
        double kwh = 0.002 + (0.005 - 0.002) * random.nextDouble();

        EnergyMessage message = new EnergyMessage();
        message.setKwh(kwh);
        message.setDatetime(LocalDateTime.now());

        sender.sendEnergyMessage(message);
    }
}
