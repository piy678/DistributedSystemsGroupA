package org.example.energyuser;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.Random;

@Component
public class EnergyGenerator {

    private final EnergyUserSender sender;
    private final Random random = new Random();

    public EnergyGenerator(EnergyUserSender sender) {
        this.sender = sender;
    }

    // Nachricht alle 1–5 Sekunden senden
    @Scheduled(fixedDelayString = "#{T(java.util.concurrent.ThreadLocalRandom).current().nextInt(1000, 5000)}")
    public void generateEnergy() {
        double kwh = calculateKWh();

        EnergyMessage message = new EnergyMessage();
        message.setKwh(kwh);
        message.setDatetime(ZonedDateTime.now(ZoneOffset.UTC).toInstant());


        sender.sendEnergyMessage(message);
    }

    // Realistische Verbrauchslogik nach Tageszeit
    private double calculateKWh() {
        LocalTime now = LocalTime.now();
        double timeOfDayFactor;

        if (now.isBefore(LocalTime.of(5, 0)) || now.isAfter(LocalTime.of(23, 0))) {
            return 0.0005;
        }

        if ((now.isAfter(LocalTime.of(6, 0)) && now.isBefore(LocalTime.of(9, 0))) ||
                (now.isAfter(LocalTime.of(17, 0)) && now.isBefore(LocalTime.of(21, 0)))) {
            timeOfDayFactor = 1.0;
        } else if (now.isAfter(LocalTime.of(12, 0)) && now.isBefore(LocalTime.of(16, 0))) {
            timeOfDayFactor = 0.3;
        } else {
            timeOfDayFactor = 0.6;
        }

        double base = 0.002;
        double range = 0.004;
        double kwh = base + (random.nextDouble() * range * timeOfDayFactor);
        return Math.round(kwh * 1000.0) / 1000.0;
    }
}
