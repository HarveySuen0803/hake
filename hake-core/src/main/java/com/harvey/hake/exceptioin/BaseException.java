package com.harvey.hake.exceptioin;

import com.harvey.hake.common.Result;

public class BaseException extends RuntimeException {
    private final int code;
    
    private final String message;

    public BaseException(int code, String message) {
        super(message, null);
        this.code = code;
        this.message = message;
    }
    
    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
    
    public <T> BaseException(Result<T> result) {
        this(result.getCode(), result.getMessage());
    }
    
    public <T> BaseException(Result<T> result, Throwable cause) {
        this(result.getCode(), result.getMessage(), cause);
    }
    
    public int getCode() {
        return code;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}