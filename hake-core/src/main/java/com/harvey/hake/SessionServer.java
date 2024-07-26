package com.harvey.hake;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * @author harvey
 */
public class SessionServer implements Callable<Channel> {
    private static final Logger logger = LoggerFactory.getLogger(SessionServer.class);
    
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    
    private Channel channel;
    
    @Override
    public Channel call() throws Exception {
        ChannelFuture channelFuture = null;
        
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childHandler(new SocketChannelInitializer());
            
            channelFuture = serverBootstrap
                .bind(new InetSocketAddress(20113))
                .syncUninterruptibly();
            
            channel = channelFuture.channel();
        } catch (Exception e) {
            logger.error("socket server start error.", e);
        } finally {
            if (channelFuture != null && channelFuture.isSuccess()) {
                logger.info("socket server start done.");
            } else {
                logger.error("socket server start error.");
            }
        }
        
        return channel;
    }
}
