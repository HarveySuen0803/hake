package com.harvey.hake.api;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.harvey.hake.exceptioin.ClientException;
import com.harvey.hake.common.HttpUri;
import com.harvey.hake.common.Result;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;

import java.nio.charset.StandardCharsets;

/**
 * @author harvey
 */
public class RpcRequestClient extends AbstractRequestClient {
    private RpcRequestClient() {
    }
    
    private static class SingletonHolder {
        private static final RpcRequestClient INSTANCE = new RpcRequestClient();
    }
    
    public static RpcRequestClient getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    @Override
    protected void check(FullHttpRequest request) {
        String requestUri = request.uri();
        if (!HttpUri.API_RPC.equals(requestUri)) {
            throw new ClientException(Result.failure("Error request path"));
        }
        
        String requestMethod = request.method().toString();
        if (!"POST".equalsIgnoreCase(requestMethod)) {
            throw new ClientException(Result.failure("Error request method"));
        }
        
        String contentType = request.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (!"application/json".equalsIgnoreCase(contentType)) {
            throw new ClientException(Result.failure("Error request content-type"));
        }
    }
    
    @Override
    protected Result<Object> send(FullHttpRequest request) {
        GenericReferenceDto genericReferenceDto = buildGenericReferenceDto(request);
        
        String interfaceName = genericReferenceDto.getInterfaceName();
        String methodName = genericReferenceDto.getMethodName();
        String[] parameterTypes = genericReferenceDto.getParameterTypes().toArray(String[]::new);
        Object[] parameters = genericReferenceDto.getParameters().toArray(Object[]::new);
        
        GenericReferenceRegistry genericReferenceRegistry = GenericReferenceRegistry.getInstance();
        
        GenericReferenceProxy genericReferenceProxy = genericReferenceRegistry.resolve(interfaceName, methodName, parameterTypes);
        
        Object result = genericReferenceProxy.$invoke(parameters);
        
        return Result.success(result);
    }
    
    private GenericReferenceDto buildGenericReferenceDto(FullHttpRequest request) throws ClientException {
        String requestBodyJson = request.content().toString(StandardCharsets.UTF_8);
        
        GenericReferenceDto genericReferenceDto = JSON.parseObject(requestBodyJson, GenericReferenceDto.class);
        
        if (StrUtil.isBlank(genericReferenceDto.getInterfaceName())) {
            throw new ClientException(ApiResult.INTERFACE_NAME_INVALID);
        }
        
        if (StrUtil.isBlank(genericReferenceDto.getMethodName())) {
            throw new ClientException(ApiResult.METHOD_NAME_INVALID);
        }
        
        if (StrUtil.isBlank(genericReferenceDto.getReturnType())) {
            throw new ClientException(ApiResult.RETURN_TYPE_INVALID);
        }
        
        return genericReferenceDto;
    }
}
