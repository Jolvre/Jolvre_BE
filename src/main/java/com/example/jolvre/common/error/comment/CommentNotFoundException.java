package com.example.jolvre.common.error.comment;

import com.example.jolvre.common.error.EntityNotFoundException;
import com.example.jolvre.common.error.ErrorCode;

public class CommentNotFoundException extends EntityNotFoundException {
    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
