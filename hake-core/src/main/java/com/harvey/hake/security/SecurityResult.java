package com.harvey.hake.security;

import com.harvey.hake.common.Result;

/**
 * @author harvey
 */
public class SecurityResult {
    public static final Result USERNAME_INVALID = new Result(3001, "username invalid");
    
    public static final Result PASSWORD_INVALID = new Result(3002, "password invalid");
    
    public static final Result USERNAME_PASSWORD_ERROR = new Result(3003, "username or password is error");
}
