package com.harvey.hake.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Bean;

/**
 * @author harvey
 */
public class JwtRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        return new SimpleAuthenticationInfo(jwtToken.getPrincipal(), jwtToken.getCredentials(), getName());
    }
    
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new SimpleCredentialsMatcher();
    }
}
