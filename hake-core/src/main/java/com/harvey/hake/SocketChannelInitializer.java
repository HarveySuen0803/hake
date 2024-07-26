package com.harvey.hake;

import com.harvey.hake.api.ApiHandler;
import com.harvey.hake.exceptioin.ExceptionHandler;
import com.harvey.hake.security.SecurityHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author harvey
 */
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast(new GatewayHandler());
        pipeline.addLast(new SecurityHandler());
        pipeline.addLast(new ApiHandler());
        pipeline.addLast(new ExceptionHandler());
    }
}
