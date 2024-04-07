package com.example.jolvre.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST"),
    STUDENT("ROLE_STUDENT"),
    USER("ROLE_USER");

    private final String key;
}
