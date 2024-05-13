package com.example.jolvre.common.error.user;

import com.example.jolvre.common.error.BusinessException;
import com.example.jolvre.common.error.ErrorCode;

public class UserAccessDeniedException extends BusinessException {
    public UserAccessDeniedException() {
        super(ErrorCode.USER_ACCESS_DENIED);
    }
}
