package org.example.energyuser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

@Component
public class EnergyGenerator {

    private final EnergyUserSender sender;
    private final Random random = new Random();

    public EnergyGenerator(EnergyUserSender sender) {
        this.sender = sender;
    }

    @Scheduled(fixedDelay = 3000)
    public void generateEnergy() {
        double baseKwh;

        // Zeitabhängige Verbrauchslogik
        LocalTime now = LocalTime.now();
        if (now.isAfter(LocalTime.of(7, 0)) && now.isBefore(LocalTime.of(10, 0))) {
            // Morgens: hoher Verbrauch
            baseKwh = 0.005 + (0.01 - 0.005) * random.nextDouble();
        } else if (now.isAfter(LocalTime.of(18, 0)) && now.isBefore(LocalTime.of(21, 0))) {
            // Abends: hoher Verbrauch
            baseKwh = 0.005 + (0.01 - 0.005) * random.nextDouble();
        } else {
            // Sonst geringer Verbrauch
            baseKwh = 0.001 + (0.004 - 0.001) * random.nextDouble();
        }

        EnergyMessage message = new EnergyMessage();
        message.setKwh(baseKwh);
        message.setDatetime(LocalDateTime.now());

        sender.sendEnergyMessage(message);
    }
}
