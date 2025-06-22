package org.example.energyuser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EnergyUserSender {

    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.usage.exchange:energy-exchange}")
    private String exchange;

    @Value("${rabbitmq.usage.routing-key:energy.data}")
    private String routingKey;


    public EnergyUserSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEnergyMessage(EnergyMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Gesendet: " + message);
    }

}
