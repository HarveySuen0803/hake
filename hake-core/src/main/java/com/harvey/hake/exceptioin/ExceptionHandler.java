package com.harvey.hake.exceptioin;

import com.harvey.hake.support.HttpResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author harvey
 */
public class ExceptionHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        
        HttpResponse.write(context, cause);
        
        context.close();
    }
}
