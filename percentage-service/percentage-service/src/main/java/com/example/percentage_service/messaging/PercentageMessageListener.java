package com.example.percentage_service.messaging;

import com.example.percentage_service.service.PercentageCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

@Slf4j
@Component
public class PercentageMessageListener {

    private final PercentageCalculatorService calculatorService;

    public PercentageMessageListener(PercentageCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @RabbitListener(queues = "${rabbitmq.percentage.queue:current-percentage-queue}")
    public void handleUsageUpdate(Map<String, Object> msg) {
        try {
            // 1. Versuche zuerst direkt ein "hour"-Feld zu lesen
            Object hourObj = msg.get("hour");
            LocalDateTime hour;

            if (hourObj != null) {
                hour = LocalDateTime.parse(hourObj.toString());
            } else {
                // 2. Wenn nicht vorhanden, versuche "datetime" zu verarbeiten
                Object dtObj = msg.get("datetime");
                if (dtObj == null) {
                    log.warn("Empfangene Nachricht enthält kein 'hour' oder 'datetime'-Feld: {}", msg);
                    return;
                }

                double epochSeconds = Double.parseDouble(dtObj.toString());
                hour = LocalDateTime.ofEpochSecond((long) epochSeconds, 0, java.time.ZoneOffset.UTC)
                        .withMinute(0).withSecond(0).withNano(0);
            }

            calculatorService.calculateForHour(hour);
            log.info("Prozentberechnung ausgeführt für Stunde: {}", hour);
        } catch (Exception e) {
            log.error("Fehler beim Verarbeiten der usage-update Nachricht: {}", msg, e);
        }
    }

}
