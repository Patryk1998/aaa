package com.zwirownia.stream;

import com.zwirownia.entities.dto.nbpApi.RatesDto;
import com.zwirownia.stream.delay.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableBinding(HelloBinding.class)
public class ProducerController {

    private MessageChannel odd;
    private MessageChannel even;
    private Producer producer;

    @Autowired
    private RestTemplate restTemplate;

    public ProducerController(Producer producer, HelloBinding binding) {
        this.odd = binding.odd();
        this.even = binding.even();
        this.producer = producer;
    }

    @RequestMapping("/get/{name}")
    public void publish(@PathVariable String name) {
        String greeting = "Hello, " + name + "!";
        Message<String> msg = MessageBuilder.withPayload(greeting)
                .build();
        if(Integer.parseInt(name)%2==0) {
            even.send(msg);
        } else odd.send(msg);
    }

    @RequestMapping("/get1")
    public void get1() {
        producer.createCustomer();
    }

    @RequestMapping("/get2")
    public void get2() {
        producer.createCustomerWithDelay();
    }

//    @RequestMapping(value = "/ceny", method = RequestMethod.GET)
//    public RatesDto asd() {
//        for(int i=0; i<100000; i++) {
//            System.out.println(i);
//        }
//        return new RatesDto("aaaaa");
//    }
//
//    @RequestMapping("/asdl")
//    public void asd1() {
//        RatesDto ratesDto = restTemplate.getForObject("http://localhost:8080/ceny", RatesDto.class);
//        System.out.println(ratesDto.getValue());
//    }
    //DeferredResult

}
