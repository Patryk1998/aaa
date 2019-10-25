package com.zwirownia.stream.delay;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public Exchange eventExchange() {
        TopicExchange exchange = new TopicExchange("eventExchange");
        exchange.setDelayed(true);
        return exchange;
    }

    @Bean
    public Producer customerService(RabbitTemplate rabbitTemplate, Exchange eventExchange) {
        return new Producer(rabbitTemplate, eventExchange);
    }

}
