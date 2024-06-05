package com.example.jolvre.common.error.exhibition;

import com.example.jolvre.common.error.EntityNotFoundException;
import com.example.jolvre.common.error.ErrorCode;

public class ExhibitNotFoundException extends EntityNotFoundException {
    public ExhibitNotFoundException() {
        super(ErrorCode.EXHIBIT_NOT_FOUND);
    }
}
