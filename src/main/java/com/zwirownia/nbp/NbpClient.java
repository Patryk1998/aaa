package com.zwirownia.nbp;

import com.zwirownia.entities.dto.nbpApi.RatesDto;
import com.zwirownia.entities.dto.nbpApi.WalutaResponseDto;
import com.zwirownia.support.nbp.CacheConfig;
import com.zwirownia.support.nbp.CounterService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

@Component
@EnableCaching
@EnableScheduling
public class NbpClient {

//    @Autowired
//    private RestOperations restTemplate;

    @Autowired
    private NbpConfig nbpConfig;

    @Autowired
    private CounterService counterService;

    public URI buildURL() {
        URI uri = UriComponentsBuilder.fromHttpUrl(nbpConfig.getUrl() + nbpConfig.getCurrency())
                .queryParam("?format", nbpConfig.getFormat())
                .build().encode().toUri();
        return uri;
    }

    @Cacheable(CacheConfig.CACHE_ONE)
    public WalutaResponseDto getCurrency() {
//        try {
//            counterService.increment("NbpClient");
//            Thread.sleep(5000);
//            return restTemplate.getForObject(buildURL(), WalutaResponseDto.class);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
        RatesDto ratesDto = new RatesDto("34");
        return new WalutaResponseDto("asd", "asd", Arrays.asList(ratesDto));
    }

    @Bean
    @Qualifier("restTemplate")
    public RestOperations restTemplate() {
        return new RestTemplate();
//        RestTemplate rt = new RestTemplate();
//        HttpComponentsClientHttpRequestFactory rf = (HttpComponentsClientHttpRequestFactory)rt.getRequestFactory();
//        rf.setReadTimeout(1000);
//        rf.setConnectTimeout(1000);
//        return rt;
    }

}
