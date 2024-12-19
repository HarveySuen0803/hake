package com.harvey.hake.security;

import com.harvey.hake.common.HttpUri;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author harvey
 */
public class SecurityHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(SecurityHandler.class);
    
    private final JwtTokenService jwtTokenService;
    
    public SecurityHandler() {
        this.jwtTokenService = JwtTokenServiceImpl.getInstance();
        
        JwtRealm jwtRealm = new JwtRealm();
        
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(jwtRealm);
        
        SecurityUtils.setSecurityManager(securityManager);
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext context, FullHttpRequest request) {
        if (isExcludedRequest(request)) {
            context.fireChannelRead(request.retain());
            return;
        }
        
        String authorization = request.headers().get(HttpHeaderNames.AUTHORIZATION);
        
        JwtToken jwtToken = jwtTokenService.buildToken(authorization);
        
        Subject subject = SecurityUtils.getSubject();
        
        subject.login(jwtToken);
        
        context.fireChannelRead(request.retain());
    }
    
    private boolean isExcludedRequest(FullHttpRequest request) {
        String requestUri = request.uri();
        
        if (HttpUri.LOGIN.equals(requestUri)) {
            return true;
        }
        
        if (HttpUri.REGISTER.equals(requestUri)) {
            return true;
        }
        
        return false;
    }
}
