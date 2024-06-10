package com.example.jolvre.common.error.user;

import com.example.jolvre.common.error.EntityNotFoundException;
import com.example.jolvre.common.error.ErrorCode;

public class UserAlreadyExistException extends EntityNotFoundException {

    public UserAlreadyExistException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
