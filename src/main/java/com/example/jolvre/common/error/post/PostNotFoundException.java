package com.example.jolvre.common.error.post;

import com.example.jolvre.common.error.EntityNotFoundException;
import com.example.jolvre.common.error.ErrorCode;

public class PostNotFoundException extends EntityNotFoundException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
