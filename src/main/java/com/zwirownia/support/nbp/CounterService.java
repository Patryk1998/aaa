package com.zwirownia.support.nbp;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CounterService implements ICounterService {

    private MeterRegistry meterRegistry;

    public CounterService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void increment(String name) {
        meterRegistry.counter(name, new ArrayList<Tag>()).increment();
    }

    @Override
    public void increment(String name, String... tags) {
        meterRegistry.counter(name, tags).increment();
    }
}
