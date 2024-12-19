package com.harvey.hake.api;

import com.harvey.hake.GatewayContext;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author harvey
 */
public class GenericReferenceRegistry {
    private final GatewayContext gatewayContext;
    
    private GenericReferenceRegistry() {
        this.gatewayContext = GatewayContext.getInstance();
        
        register(
            "sayHello",
            new String[]{"java.lang.String", "java.lang.String"}
        );
    }
    
    private static class Holder {
        private static final GenericReferenceRegistry INSTANCE = new GenericReferenceRegistry();
    }
    
    public static GenericReferenceRegistry getInstance() {
        return Holder.INSTANCE;
    }
    
    private final Set<String> serviceKeySet = new HashSet<>();
    
    private final Map<String, GenericReferenceProxy> genericReferenceProxyCache = new ConcurrentHashMap<>();
    
    public void register(String methodName, String[] parameterTypes) {
        String serviceKey = buildServiceKey(methodName, parameterTypes);
        
        boolean isAdded = serviceKeySet.add(serviceKey);
        if (!isAdded) {
            throw new RuntimeException("Service " + serviceKey + " is already known to the GenericReferenceRegistry.");
        }
    }
    
    public GenericReferenceProxy resolve(String interfaceName, String methodName, String[] parameterTypes) {
        String serviceKey = buildServiceKey(methodName, parameterTypes);
        if (!serviceKeySet.contains(serviceKey)) {
            throw new RuntimeException("Service " + serviceKey + " is not known to the GenericReferenceRegistry.");
        }
        
        return genericReferenceProxyCache.computeIfAbsent(serviceKey, key -> {
            GenericService genericService = gatewayContext.getReferenceConfig(interfaceName).get();
            return getGenericReferenceProxy(methodName, parameterTypes, genericService);
        });
    }
    
    private GenericReferenceProxy getGenericReferenceProxy(String methodName, String[] parameterTypes, GenericService genericService) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Object.class);
        enhancer.setInterfaces(new Class[]{GenericReferenceProxy.class});
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> genericService.$invoke(methodName, parameterTypes, (Object[]) args[0]));

        return (GenericReferenceProxy) enhancer.create();
    }
    
    // eg: sayHell(java.lang.String,java.lang.Object)#java.lang.Object
    private String buildServiceKey(String methodName, String[] parameterTypes) {
        StringBuilder serviceKeyBuilder = new StringBuilder();
        
        serviceKeyBuilder.append(methodName);
        
        serviceKeyBuilder.append("(");
        
        for (String parameterClass : parameterTypes) {
            serviceKeyBuilder.append(parameterClass);
            serviceKeyBuilder.append(",");
        }
        
        serviceKeyBuilder.append(")");
        
        return serviceKeyBuilder.toString();
    }
}
