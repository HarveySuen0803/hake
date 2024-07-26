package com.harvey.hake.api;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.harvey.hake.exceptioin.ClientException;
import com.harvey.hake.common.HttpUri;
import com.harvey.hake.common.Result;
import com.harvey.hake.security.SecurityService;
import com.harvey.hake.security.SecurityServiceImpl;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;

import java.nio.charset.StandardCharsets;

/**
 * @author harvey
 */
public class HttpRequestClient extends AbstractRequestClient {
    private final SecurityService securityService;
    
    private HttpRequestClient() {
        this.securityService = SecurityServiceImpl.getInstance();
    }
    
    private static class SingletonHolder {
        private static final HttpRequestClient INSTANCE = new HttpRequestClient();
    }
    
    public static HttpRequestClient getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    @Override
    protected void check(FullHttpRequest request) {
        String requestUri = request.uri();
        if (!requestUri.startsWith(HttpUri.API_HTTP)) {
            throw new ClientException(ApiResult.REQUEST_PATH_INVALID);
        }
        
        String contentType = request.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (!"application/json".equalsIgnoreCase(contentType)) {
            throw new ClientException(ApiResult.REQUEST_CONTENT_TYPE_INVALID);
        }
    }
    
    @Override
    protected Result<Object> send(FullHttpRequest request) {
        String requestUri = request.uri();
        
        if (HttpUri.LOGIN.equals(requestUri)) {
            String requestBodyJson = request.content().toString(StandardCharsets.UTF_8);
            
            JSONObject jsonObject = JSON.parseObject(requestBodyJson);
            String username = (String) jsonObject.get("username");
            String password = (String) jsonObject.get("password");
            
            LoginDto loginDto = new LoginDto(username, password);
            
            String tokenStr = securityService.login(loginDto);
            
            return Result.success(tokenStr);
        }
        
        return Result.success();
    }
    
    private LoginDto buildLoginDto(FullHttpRequest request) {
        String requestBodyJson = request.content().toString(StandardCharsets.UTF_8);
        
        LoginDto loginDto = JSON.parseObject(requestBodyJson, LoginDto.class);
        
        return loginDto;
    }
}
