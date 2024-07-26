package com.harvey.hake;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.jupiter.api.Test;

/**
 * @author harvey
 */
public class SessionServerTest {
    @Test
    public void testRpc() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("hake-test-consumer");
        applicationConfig.setQosEnable(false);
        
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setRegister(false);
        
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.harvey.hake.test.HelloService");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric("true");
        
        DubboBootstrap dubboBootstrap = DubboBootstrap.getInstance();
        dubboBootstrap.application(applicationConfig)
            .registry(registryConfig)
            .reference(referenceConfig)
            .start();
        
        GenericService genericService = referenceConfig.get();
        
        String result = (String) genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"world"});
        
        System.out.println(result);
    }
}
