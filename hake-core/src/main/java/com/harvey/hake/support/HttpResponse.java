package com.harvey.hake.support;

import com.alibaba.fastjson2.JSON;
import com.harvey.hake.exceptioin.BaseException;
import com.harvey.hake.common.Result;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

/**
 * @author harvey
 */
public class HttpResponse {
    public static <T> ChannelFuture write(ChannelHandlerContext context, Result<T> result) {
        return context.channel().writeAndFlush(buildResponse(result));
    }
    
    public static ChannelFuture write(ChannelHandlerContext context, BaseException e) {
        return write(context, Result.failure(e.getCode(), e.getMessage()));
    }
    
    public static ChannelFuture write(ChannelHandlerContext context, Throwable e) {
        if (e instanceof BaseException) {
            return write(context, (BaseException) e);
        } else {
            return write(context, Result.failure(e.getMessage()));
        }
    }
    
    private static <T> DefaultFullHttpResponse buildResponse(Result<T> result) {
        
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        
        response.content().writeBytes(JSON.toJSONBytes(result));
        
        response.headers()
            .add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON + "; charset=UTF-8")
            .add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes())
            .add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
            .add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
            .add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "*")
            .add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE")
            .add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        
        return response;
    }
}
