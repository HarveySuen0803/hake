package com.harvey.hake;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author harvey
 */
public class GatewayHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(GatewayHandler.class);
    
    @Override
    protected void channelRead0(ChannelHandlerContext context, FullHttpRequest request) throws Exception {
        logger.info("Gateway receives a request, request：{}, context：{}", request, context);
        
        context.fireChannelRead(request.retain());
    }
}
