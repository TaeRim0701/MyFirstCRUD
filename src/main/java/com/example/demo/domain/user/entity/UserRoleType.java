package com.example.demo.domain.user.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum UserRoleType {

    ADMIN("어드민"),
    USER("유저");

    private final String description;

    UserRoleType(final String description) {
        this.description = description;
    }
}
