package com.harvey.hake.security;

import com.harvey.hake.api.LoginDto;
import com.harvey.hake.exceptioin.ClientException;

/**
 * @author harvey
 */
public class SecurityServiceImpl implements SecurityService {
    private final JwtTokenService jwtTokenService;
    
    private SecurityServiceImpl() {
        this.jwtTokenService = JwtTokenServiceImpl.getInstance();
    };
    
    private static class SingletonHolder {
        private static final SecurityServiceImpl INSTANCE = new SecurityServiceImpl();
    }
    
    public static SecurityServiceImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    @Override
    public String login(LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        
        if (username.equals("harvey") && password.equals("111")) {
            return jwtTokenService.buildTokenStr(username);
        } else {
            throw new ClientException(SecurityResult.USERNAME_PASSWORD_ERROR);
        }
    }
}
