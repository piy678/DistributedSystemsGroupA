package org.example.energyproducer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EnergyProducerSender {

    private final RabbitTemplate rabbitTemplate;

    public EnergyProducerSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEnergyMessage(EnergyMessage message) {
        rabbitTemplate.convertAndSend("energy-data", message);
        System.out.println("Gesendet: " + message);
    }
}
