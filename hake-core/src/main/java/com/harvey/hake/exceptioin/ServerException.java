package com.harvey.hake.exceptioin;

import com.harvey.hake.common.Result;

/**
 * @author harvey
 */
public class ServerException extends BaseException {
    public ServerException(int code, String message) {
        super(code, message);
    }
    
    public ServerException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
    
    public <T> ServerException(Result<T> result) {
        super(result);
    }
    
    public <T> ServerException(Result<T> result, Throwable cause) {
        super(result, cause);
    }
}
