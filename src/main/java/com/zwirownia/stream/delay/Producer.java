package com.zwirownia.stream.delay;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    private final Exchange exchange;

    public Producer(RabbitTemplate rabbitTemplate, Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void createCustomer() {
        String routingKey = "customer.created";
        String message = "customer created";
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, message);
    }

    public void createCustomerWithDelay() {
        String routingKey = "customer.created";
        String message = "delay";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDelay(3000);
        rabbitTemplate.send(exchange.getName(), routingKey, MessageBuilder.withBody(message.getBytes()).andProperties(messageProperties).build());
    }


}
