package com.example.jolvre.common.error.exhibition;

import com.example.jolvre.common.error.EntityNotFoundException;
import com.example.jolvre.common.error.ErrorCode;

public class DiaryNotFoundException extends EntityNotFoundException {
    public DiaryNotFoundException() {
        super(ErrorCode.DIARY_NOT_FOUND);
    }
}
