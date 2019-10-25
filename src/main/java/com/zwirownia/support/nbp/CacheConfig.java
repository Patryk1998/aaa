package com.zwirownia.support.nbp;

import com.google.common.cache.CacheBuilder;
import io.micrometer.core.instrument.binder.cache.GuavaCacheMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.guava.GuavaCache;
import sun.security.util.Cache;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {


    public final static String CACHE_ONE = "currency";
    public final static String CACHE_TWO = "cacheTwo";

    @Bean
    @Override
    public CacheManager cacheManager() {

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        GuavaCache cache1 = new GuavaCache(CACHE_ONE, CacheBuilder.newBuilder()
                .expireAfterWrite(20, TimeUnit.SECONDS)
                .recordStats()
                .build());

        GuavaCache cache2 = new GuavaCache(CACHE_TWO, CacheBuilder.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build());

        cacheManager.setCaches(Arrays.asList(cache1,cache2));
        return cacheManager;
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }

}
