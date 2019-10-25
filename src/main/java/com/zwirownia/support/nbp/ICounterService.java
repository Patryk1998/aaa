package com.zwirownia.support.nbp;

public interface ICounterService {

    void increment(String name);
    void increment(String name, String ...tags);
}
