package com.sxito.wcr.config.exception;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message){
        super(message);
    }
}
