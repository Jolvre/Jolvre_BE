package com.example.jolvre.common.error.group;

import com.example.jolvre.common.error.EntityNotFoundException;
import com.example.jolvre.common.error.ErrorCode;

public class GroupInviteDuplicateException extends EntityNotFoundException {
    public GroupInviteDuplicateException() {
        super(ErrorCode.GROUP_INVITE_DUPLICATE_FOUND);
    }
}
