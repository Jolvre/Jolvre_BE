package com.example.jolvre.common.error.group;

import com.example.jolvre.common.error.EntityNotFoundException;
import com.example.jolvre.common.error.ErrorCode;

public class GroupExhibitNotFoundException extends EntityNotFoundException {
    public GroupExhibitNotFoundException() {
        super(ErrorCode.GROUP_EXHIBIT_NOT_FOUND);
    }
}
