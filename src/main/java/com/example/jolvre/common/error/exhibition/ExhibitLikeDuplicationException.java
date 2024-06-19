package com.example.jolvre.common.error.exhibition;

import com.example.jolvre.common.error.EntityNotFoundException;
import com.example.jolvre.common.error.ErrorCode;

public class ExhibitLikeDuplicationException extends EntityNotFoundException {
    public ExhibitLikeDuplicationException() {
        super(ErrorCode.EXHIBIT_LIKE_DUPLICATION);
    }
}
