package org.example.energyuser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EnergyUserSender {

    private final RabbitTemplate rabbitTemplate;
    @Value("${energy.queue}")
    private String queueName;

    public EnergyUserSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEnergyMessage(EnergyMessage message) {
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.println("Gesendet: " + message);
    }
}
