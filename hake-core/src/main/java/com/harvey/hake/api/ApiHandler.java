package com.harvey.hake.api;

import com.harvey.hake.common.Result;
import com.harvey.hake.support.HttpResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author harvey
 */
public class ApiHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(ApiHandler.class);
    
    @Override
    protected void channelRead0(ChannelHandlerContext context, FullHttpRequest request) {
        RequestClientRegistry requestClientRegistry = RequestClientRegistry.getInstance();
        
        RequestClient requestClient = requestClientRegistry.get(request);
        
        Result result = requestClient.sendRequest(request);
        
        HttpResponse.write(context, result);
    }
}
