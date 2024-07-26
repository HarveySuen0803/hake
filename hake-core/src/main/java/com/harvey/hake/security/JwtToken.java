package com.harvey.hake.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author harvey
 */
public class JwtToken implements AuthenticationToken {
    private final String principal;
    
    private final String credentials;
    
    public JwtToken(String principal, String credentials) {
        this.principal = principal;
        this.credentials = credentials;
    }
    
    @Override
    public Object getPrincipal() {
        return principal;
    }
    
    @Override
    public Object getCredentials() {
        return credentials;
    }
}
