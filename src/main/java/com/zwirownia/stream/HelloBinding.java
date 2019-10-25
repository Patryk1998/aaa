package com.zwirownia.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.binder.rabbit.config.RabbitMessageChannelBinderConfiguration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import javax.persistence.OneToMany;

public interface HelloBinding {

    @Output("even")
    MessageChannel even();

    @Output("odd")
    MessageChannel odd();

    @Input("input")
    SubscribableChannel send();
}
