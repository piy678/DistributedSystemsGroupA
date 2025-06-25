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
            LocalDateTime hour;

            if (msg.containsKey("hour")) {
                hour = LocalDateTime.parse(msg.get("hour").toString());
            } else if (msg.containsKey("datetime")) {
                Object dtObj = msg.get("datetime");
                String dtStr = dtObj.toString();

                if (dtStr.matches("^\\d+(\\.\\d+)?([eE][+-]?\\d+)?$")) {
                    // Epoch-Format, z. B. 1.7506E9
                    double epochSeconds = Double.parseDouble(dtStr);
                    hour = LocalDateTime.ofEpochSecond((long) epochSeconds, 0, java.time.ZoneOffset.UTC)
                            .withMinute(0).withSecond(0).withNano(0);
                } else {
                    // ISO 8601 Format
                    hour = LocalDateTime.parse(dtStr)
                            .withMinute(0).withSecond(0).withNano(0);

                }
            } else {
                log.warn("Empfangene Nachricht enthält kein 'hour' oder 'datetime'-Feld: {}", msg);
                return;
            }

            calculatorService.calculateForHour(hour);
            log.info("Prozentberechnung ausgeführt für Stunde: {}", hour);

        } catch (Exception e) {
            log.error("Fehler beim Verarbeiten der usage-update Nachricht: {}", msg, e);
        }
    }

    public void handleUsageUpdate(UsageUpdateMessage msg) {
        LocalDateTime hour = LocalDateTime.parse(msg.getHour())
                .withMinute(0).withSecond(0).withNano(0);
        calculatorService.calculateForHour(hour);
    }


}