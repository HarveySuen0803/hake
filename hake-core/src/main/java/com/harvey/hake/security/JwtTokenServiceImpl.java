package com.harvey.hake.security;

import cn.hutool.core.util.StrUtil;
import com.harvey.hake.exceptioin.ClientException;
import com.harvey.hake.common.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * @author harvey
 */
public class JwtTokenServiceImpl implements JwtTokenService {
    private static final Key SECURITY_KEY = Keys.hmacShaKeyFor("security_key_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx".getBytes());
    
    private JwtTokenServiceImpl() {
    }
    
    private static class SingletonHolder {
        private static final JwtTokenServiceImpl INSTANCE = new JwtTokenServiceImpl();
    }
    
    public static JwtTokenServiceImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    @Override
    public JwtToken buildToken(String tokenStr) {
        Claims claims = getClaims(tokenStr);
        
        String principal = claims.getSubject();
        
        return new JwtToken(principal, tokenStr);
    }
    
    private Claims getClaims(String tokenStr) {
        if (StrUtil.isBlank(tokenStr)) {
            throw new ClientException(Result.UNAUTHORIZED);
        }
        
        JwtParser jwtParser = Jwts.parserBuilder()
            .setSigningKey(SECURITY_KEY)
            .build();
        
        Claims claims = jwtParser
            .parseClaimsJws(tokenStr)
            .getBody();
        if (claims == null) {
            throw new ClientException(Result.UNAUTHORIZED);
        }
        
        return claims;
    }
    
    @Override
    public String buildTokenStr(String principal) {
        Map<String, Object> claims = new HashMap<>();
        
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(principal)
            .signWith(SECURITY_KEY)
            .compact();
    }
}
