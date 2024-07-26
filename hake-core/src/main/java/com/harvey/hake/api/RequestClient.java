package com.harvey.hake.api;

import com.harvey.hake.common.Result;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author harvey
 */
public interface RequestClient {
    <T> Result<T> sendRequest(FullHttpRequest request);
}
