package com.harvey.hake.api;

import com.harvey.hake.common.HttpUri;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author harvey
 */
public class RequestClientRegistry {
    private static class SingletonHolder {
        private static final RequestClientRegistry INSTANCE = new RequestClientRegistry();
    }
    
    public static RequestClientRegistry getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public RequestClient get(FullHttpRequest request) {
        String requestUri = request.uri();
        
        if (requestUri.startsWith(HttpUri.API_HTTP)) {
            return HttpRequestClient.getInstance();
        } else if (requestUri.startsWith(HttpUri.API_RPC)) {
            return RpcRequestClient.getInstance();
        }
        
        return null;
    }
}
