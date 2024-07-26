package com.harvey.test;

/**
 * @author harvey
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
