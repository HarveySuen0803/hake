package com.harvey.test;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

/**
 * @author harvey
 */
public class HelloApplication {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("hake-test-service");
        
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);
        
        ServiceConfig<HelloService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(HelloService.class);
        serviceConfig.setRef(helloService);
        serviceConfig.setVersion("1.0.0");
        
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig)
            .registry(registryConfig)
            .protocol(protocolConfig)
            .service(serviceConfig)
            .start()
            .await();
    }
}
