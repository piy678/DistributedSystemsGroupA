package com.example.usage_service.messaging;

import com.example.usage_service.model.EnergyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.example.usage_service.service.UsageAggregatorService;

@Slf4j
@Component
public class UsageMessageListener {

    private final UsageAggregatorService aggregatorService;

    public UsageMessageListener(UsageAggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @RabbitListener(queues = "${rabbitmq.usage.queue:energy-data}")
    public void handleMessage(EnergyMessage message) {
        log.info("Nachricht empfangen: {}", message);
        aggregatorService.processMessage(message);
    }
}
