package com.harvey.hake;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author harvey
 */
public class HakeApplication {
    private static final Logger logger = LoggerFactory.getLogger(HakeApplication.class);
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SessionServer server = new SessionServer();
        
        Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
        Channel channel = future.get();
        if (null == channel) {
            throw new RuntimeException("netty server start error channel is null");
        }
        
        while (!channel.isActive()) {
            logger.info("starting server ...");
            try { TimeUnit.MILLISECONDS.sleep(1000); } catch (Exception e) { e.printStackTrace(); }
        }
        logger.info("server listen on {}", channel.localAddress());
        
        try { TimeUnit.SECONDS.sleep(Long.MAX_VALUE); } catch (Exception e) { e.printStackTrace(); }
    }
}
