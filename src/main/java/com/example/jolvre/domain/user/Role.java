package com.example.jolvre.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("GUEST"),
    STUDENT("STUDENT"),
    USER("USER");

    private final String key;
}
