package com.harvey.hake.api;

import java.util.List;

/**
 * @author harvey
 */
public class GenericReferenceDto {
    private String interfaceName;
    
    private String methodName;
    
    private List<String> parameters;
    
    private List<String> parameterTypes;
    
    private String returnType;
    
    public String getInterfaceName() {
        return interfaceName;
    }
    
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
    public String getMethodName() {
        return methodName;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public List<String> getParameters() {
        return parameters;
    }
    
    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
    
    public List<String> getParameterTypes() {
        return parameterTypes;
    }
    
    public void setParameterTypes(List<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
    
    public String getReturnType() {
        return returnType;
    }
    
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
