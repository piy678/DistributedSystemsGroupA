package org.example.energyuser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EnergyUserSender {

    private final RabbitTemplate rabbitTemplate;

    public EnergyUserSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEnergyMessage(EnergyMessage message) {
        rabbitTemplate.convertAndSend("energy-data", message);
        System.out.println("Gesendet: " + message);
    }
}
