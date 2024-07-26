package com.harvey.hake;

import cn.hutool.core.lang.Singleton;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author harvey
 */
public class GatewayContext {
    private final Map<String, ApplicationConfig> applicationConfigMap = new ConcurrentHashMap<>();
    
    private final Map<String, RegistryConfig> registryConfigMap = new ConcurrentHashMap<>();
    
    private final Map<String, ReferenceConfig> referenceConfigMap = new ConcurrentHashMap<>();
    
    private GatewayContext() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("hake-service");
        applicationConfig.setQosEnable(false);
        
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setRegister(false);
        
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.harvey.test.HelloService");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric("true");
        
        DubboBootstrap dubboBootstrap = DubboBootstrap.getInstance();
        dubboBootstrap.application(applicationConfig)
            .registry(registryConfig)
            .reference(referenceConfig)
            .start();
        
        applicationConfigMap.put("hake-service", applicationConfig);
        registryConfigMap.put("hake-service", registryConfig);
        referenceConfigMap.put("com.harvey.test.HelloService", referenceConfig);
    }
    
    private static class Holder {
        private static final GatewayContext INSTANCE = new GatewayContext();
    }
    
    public static GatewayContext getInstance() {
        return Holder.INSTANCE;
    }
    
    public ApplicationConfig getApplicationConfig(String applicationName) {
        return applicationConfigMap.get(applicationName);
    }
    
    public RegistryConfig getRegistryConfig(String applicationName) {
        return registryConfigMap.get(applicationName);
    }
    
    public ReferenceConfig<GenericService> getReferenceConfig(String interfaceName) {
        return referenceConfigMap.get(interfaceName);
    }
}
