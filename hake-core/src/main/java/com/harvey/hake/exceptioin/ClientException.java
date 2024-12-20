package com.harvey.hake.exceptioin;

import com.harvey.hake.common.Result;

/**
 * @author harvey
 */
public class ClientException extends BaseException {
    public ClientException(int code, String message) {
        super(code, message);
    }
    
    public ClientException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
    
    public <T> ClientException(Result<T> result) {
        super(result);
    }
    
    public <T> ClientException(Result<T> result, Throwable cause) {
        super(result, cause);
    }
}
