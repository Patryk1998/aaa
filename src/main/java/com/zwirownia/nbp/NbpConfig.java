package com.zwirownia.nbp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "api.nbp")
public class NbpConfig {

    private String url;

    private String currency;

    private String format;
}
