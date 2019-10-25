package com.zwirownia.stream.delay;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class EventConsumer {

    @RabbitListener(queues = "ORIGINAL_QUEUE")
    public void receiveDelay(String message) {
        System.out.println(message);
    }
}

