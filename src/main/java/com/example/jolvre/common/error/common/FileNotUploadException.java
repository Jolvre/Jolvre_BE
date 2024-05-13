package com.example.jolvre.common.error.common;

import com.example.jolvre.common.error.BusinessException;
import com.example.jolvre.common.error.ErrorCode;

public class FileNotUploadException extends BusinessException {
    public FileNotUploadException() {
        super(ErrorCode.FILE_NOT_UPLOAD);
    }
}
