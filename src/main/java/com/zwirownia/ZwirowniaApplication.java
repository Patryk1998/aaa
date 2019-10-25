package com.zwirownia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan // zamiast SpringBootApp bo nie działa @Autowired w NbpClient
//@EnableEurekaServer
public class ZwirowniaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZwirowniaApplication.class, args);
	}

}
