package com.example.usageservice.service;

import com.example.usageservice.model.EnergyMessage;
import com.example.usageservice.model.UsageHour;
import com.example.usageservice.repository.UsageHourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UsageAggregatorService {

    private final UsageHourRepository usageHourRepository;
    private final AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.usage.exchange:energy-exchange}")
    private String exchange;

    @Value("${rabbitmq.usage.routing-key:usage.updated}")
    private String routingKey;

    public UsageAggregatorService(UsageHourRepository usageHourRepository, AmqpTemplate rabbitTemplate) {
        this.usageHourRepository = usageHourRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processMessage(EnergyMessage message) {
        try {
            LocalDateTime timestamp = LocalDateTime.parse(message.getDatetime());
            LocalDateTime hour = timestamp.withMinute(0).withSecond(0).withNano(0);

            UsageHour usage = usageHourRepository.findById(hour)
                    .orElse(new UsageHour(hour, 0.0, 0.0, 0.0));

            double kwh = message.getKwh();
            String type = message.getType();

            switch (type.toUpperCase()) {
                case "PRODUCER":
                    usage.setCommunityProduced(usage.getCommunityProduced() + kwh);
                    break;

                case "USER":
                    double usedSum = usage.getCommunityUsed() + kwh;
                    double produced = usage.getCommunityProduced();
                    double over = Math.max(0, usedSum - produced);

                    usage.setCommunityUsed(Math.min(usedSum, produced));
                    usage.setGridUsed(usage.getGridUsed() + over);
                    break;

                default:
                    log.warn("Unbekannter Nachrichtentyp: {}", type);
                    return;
            }

            usageHourRepository.save(usage);
            log.info("Aggregierte Stunde {} gespeichert: {}", hour, usage);

            sendUpdateMessage(hour); // Optional: für percentage-service

        } catch (Exception e) {
            log.error("Fehler bei Verarbeitung der Nachricht: {}", message, e);
        }
    }


    private void sendUpdateMessage(LocalDateTime hour) {
        Map<String, Object> update = new HashMap<>();
        update.put("type", "USAGE_UPDATED");
        update.put("hour", hour.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        rabbitTemplate.convertAndSend(exchange, routingKey, update);
        log.info("Update-Nachricht gesendet an {} mit hour={}", exchange, hour);
    }
}
