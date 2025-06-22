package com.example.usage_service.config;

import com.example.usage_service.model.EnergyMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.usage.queue:usage-queue}")
    private String queueName;

    @Value("${rabbitmq.usage.exchange:energy-exchange}")
    private String exchangeName;

    @Value("${rabbitmq.usage.routing-key:energy.data}")
    private String routingKey;

    @Bean
    public Queue usageQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public TopicExchange usageExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding usageBinding(Queue usageQueue, TopicExchange usageExchange) {
        return BindingBuilder.bind(usageQueue).to(usageExchange).with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(mapper);
    }



    @Bean
    public AmqpTemplate myRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }


}
