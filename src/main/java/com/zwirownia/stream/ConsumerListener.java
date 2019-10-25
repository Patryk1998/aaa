package com.zwirownia.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(HelloBinding.class)
public class ConsumerListener {

    @StreamListener(target = "odd")
    public void processOdd(String msg) {
        System.out.println(msg);
    }

    @StreamListener(target = "even")
    public void processEven(String msg) {
        System.out.println(msg);
    }
}
