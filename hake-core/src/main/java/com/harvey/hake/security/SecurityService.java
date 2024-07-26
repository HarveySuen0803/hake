package com.harvey.hake.security;

import com.harvey.hake.api.LoginDto;

/**
 * @author harvey
 */
public interface SecurityService {
    String login(LoginDto loginDto);
}
