package com.harvey.hake.api;

import com.harvey.hake.common.Result;

/**
 * @author harvey
 */
public class ApiResult {
    public static final Result REQUEST_PATH_INVALID = new Result(1001, "request path invalid");
    
    public static final Result REQUEST_CONTENT_TYPE_INVALID = new Result(1002, "request content-type invalid");
    
    public static final Result INTERFACE_NAME_INVALID = new Result(2001, "interfaceName invalid");
    
    public static final Result METHOD_NAME_INVALID = new Result(2002, "methodName invalid");
    
    public static final Result RETURN_TYPE_INVALID = new Result(2003, "returnType invalid");
}
