package com.harvey.test;

/**
 * @author harvey
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name, String content) {
        return "hello " + name + ", " + content;
    }
}
