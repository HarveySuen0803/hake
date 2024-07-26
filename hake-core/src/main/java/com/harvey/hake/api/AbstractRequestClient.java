package com.harvey.hake.api;

import com.harvey.hake.common.Result;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author harvey
 */
public abstract class AbstractRequestClient implements RequestClient {
    @Override
    public final <T> Result<T> sendRequest(FullHttpRequest request) {
        check(request);
        
        Result<T> result = send(request);
        
        return result;
    }
    
    protected abstract void check(FullHttpRequest request);
    
    protected abstract <T> Result<T> send(FullHttpRequest request);
}
