package com.example.jolvre.common.error;

public class InvalidValueException extends BusinessException {
    
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
