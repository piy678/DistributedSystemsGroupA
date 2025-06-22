package com.example.percentage_service.config;

import com.example.percentage_service.messaging.UsageUpdateMessage;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
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

    @Value("${rabbitmq.percentage.queue:current-percentage-queue}")
    private String queueName;

    @Value("${rabbitmq.percentage.exchange:energy-exchange}")
    private String exchangeName;

    @Value("${rabbitmq.percentage.routing-key:energy.usage-updated}")
    private String routingKey;

    @Value("${spring.rabbitmq.host:localhost}")
    private String rabbitHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(rabbitHost);
        factory.setUsername("admin");
        factory.setPassword("admin");
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf, MessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(cf);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public Queue usageUpdateQueue() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public TopicExchange usageExchange() {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Binding usageBinding(Queue usageUpdateQueue, TopicExchange usageExchange) {
        return BindingBuilder
                .bind(usageUpdateQueue)
                .to(usageExchange)
                .with(routingKey);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();

        Map<String, Class<?>> idMapping = new HashMap<>();
        idMapping.put("java.util.HashMap", UsageUpdateMessage.class);

        typeMapper.setIdClassMapping(idMapping);
        typeMapper.setTrustedPackages("*");

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

}
