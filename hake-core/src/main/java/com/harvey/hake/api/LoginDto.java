package com.harvey.hake.api;

import cn.hutool.core.util.StrUtil;
import com.harvey.hake.exceptioin.ClientException;
import com.harvey.hake.security.SecurityResult;

/**
 * @author harvey
 */
public class LoginDto {
    private String username;
    
    private String password;
    
    public LoginDto(String username, String password) {
        if (StrUtil.isBlank(username)) {
            throw new ClientException(SecurityResult.USERNAME_INVALID);
        }
        
        if (StrUtil.isBlank(password)) {
            throw new ClientException(SecurityResult.PASSWORD_INVALID);
        }
        
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
}
