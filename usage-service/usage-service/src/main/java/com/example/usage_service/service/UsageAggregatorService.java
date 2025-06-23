package com.example.usage_service.service;

import com.example.usage_service.model.EnergyMessage;
import com.example.usage_service.model.UsageHour;
import com.example.usage_service.repository.UsageHourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UsageAggregatorService {

    private final UsageHourRepository usageHourRepository;
    private final AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.usage.exchange:energy-exchange}")
    private String exchange;

    @Value("${rabbitmq.usage.routing-key:energy.data}")
    private String routingKey;

    @Value("${rabbitmq.updates.routing-key:energy.update}")
    private String updateRoutingKey;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public UsageAggregatorService(UsageHourRepository usageHourRepository, AmqpTemplate rabbitTemplate) {
        this.usageHourRepository = usageHourRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processMessage(EnergyMessage message) {
        try {
            LocalDateTime timestamp = LocalDateTime.ofInstant(message.getDatetime().toInstant(), ZoneOffset.UTC);
            log.debug("Parsed timestamp: {}", timestamp);

            LocalDateTime hour = timestamp.withMinute(0).withSecond(0).withNano(0);
            UsageHour usageHour = usageHourRepository
                    .findById(hour)
                    .orElse(new UsageHour(hour, 0.0, 0.0, 0.0));

            double kwh = message.getKwh();
            String type = message.getType();

            switch (type.toUpperCase()) {
                case "PRODUCER":
                    usageHour.setCommunityProduced(usageHour.getCommunityProduced() + kwh);
                    break;

                case "USER":
                    usageHour.setCommunityUsed(usageHour.getCommunityUsed() + kwh);

                    double over = Math.max(0, usageHour.getCommunityUsed() - usageHour.getCommunityProduced());
                    usageHour.setGridUsed(over);
                    break;



                default:
                    log.warn("Unbekannter Nachrichtentyp: {}", type);
                    return;
            }
            usageHour.setTotalProduced(usageHour.getCommunityProduced());
            usageHour.setTotalUsed(usageHour.getCommunityUsed() + usageHour.getGridUsed());
            usageHourRepository.save(usageHour);
            log.info("Aggregierter Wert gespeichert für Stunde {}: {}", hour, usageHour);

            sendUpdateMessage(hour);




        } catch (Exception e) {
            log.error("Fehler beim Verarbeiten der Nachricht: {}", message, e);
        }
    }

    private LocalDateTime parseDatetime(String input) {
        try {
            return java.time.ZonedDateTime.parse(input).toLocalDateTime();
        } catch (Exception e1) {
            try {
                return LocalDateTime.parse(input);
            } catch (Exception e2) {
                try {

                    double epochDouble = Double.parseDouble(input);
                    long seconds = (long) epochDouble;
                    return LocalDateTime.ofInstant(
                            java.time.Instant.ofEpochSecond(seconds),
                            java.time.ZoneOffset.UTC
                    );
                } catch (Exception e3) {
                    throw new IllegalArgumentException("Ungültiges Datum: " + input);
                }
            }
        }
    }


    private void sendUpdateMessage(LocalDateTime hour) {
        Map<String, Object> update = new HashMap<>();
        update.put("type", "USAGE_UPDATED");
        update.put("hour", hour.format(FORMATTER));

        rabbitTemplate.convertAndSend(exchange, updateRoutingKey, update);
        log.info("Update-Nachricht gesendet an {} mit hour={}", exchange, hour);
    }
}
