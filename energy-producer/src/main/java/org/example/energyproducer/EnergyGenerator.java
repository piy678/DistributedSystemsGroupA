package org.example.energyproducer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EnergyGenerator {

    private final EnergyProducerSender sender;
    private final Random random = new Random();
    private static final Logger log = LoggerFactory.getLogger(EnergyGenerator.class);
    @Value("${energy.type}")
    private String type;

    @Value("${energy.association}")
    private String association;

    public EnergyGenerator(EnergyProducerSender sender) {
        this.sender = sender;
    }
    public double generateKwh() {
        int hour = LocalDateTime.now().getHour();
        double sunlightFactor = estimateSunlightFactor(hour);

        double base = 0.001; // Mindestproduktion
        double range = 0.004; // mögliche Schwankung
        double kwh = base + (random.nextDouble() * range * sunlightFactor);
        return round(kwh);
    }
    private double estimateSunlightFactor(int hour) {
        if (hour >= 10 && hour <= 16) return 1.0; // Mittags – volle Sonne
        if ((hour >= 7 && hour <= 9) || (hour >= 17 && hour <= 19)) return 0.6; // Vormittag/Abend
        return 0.2; // Früh/Nacht – wenig Sonne
    }


    private double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
    private boolean isSunny() {
        int hour = LocalDateTime.now().getHour();
        return hour >= 9 && hour <= 18;
    }

    @Scheduled(fixedDelayString = "#{T(java.util.concurrent.ThreadLocalRandom).current().nextInt(1000, 5000)}")
    public void generateEnergy() {
        double kwh = generateKwh();
        Instant now = Instant.now();

        EnergyMessage message = new EnergyMessage();
        message.setType(type);
        message.setAssociation(association);
        message.setKwh(kwh);
        message.setDatetime(now);

        sender.sendEnergyMessage(message);
        log.info("Sent energy message: {} kWh at {}", kwh, now);
    }



}
