package com.harvey.hake.security;

/**
 * @author harvey
 */
public interface JwtTokenService {
    JwtToken buildToken(String tokenStr);
    
    String buildTokenStr(String principal);
}
