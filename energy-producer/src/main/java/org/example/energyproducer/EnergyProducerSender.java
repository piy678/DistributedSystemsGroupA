package org.example.energyproducer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EnergyProducerSender {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;
    private static final Logger log = LoggerFactory.getLogger(EnergyProducerSender.class);

    @Value("${energy.type}")
    private String type;

    @Value("${energy.association}")
    private String association;

    public EnergyProducerSender(
            RabbitTemplate rabbitTemplate,
            @Value("${rabbitmq.exchange}") String exchange,
            @Value("${rabbitmq.routing-key}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void sendEnergyMessage(EnergyMessage message) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.info("Sent message to RabbitMQ: {}", message);
        } catch (Exception e) {
            log.error("Failed to send message to RabbitMQ", e);
        }
    }

}
