package com.changemakers.ishgo.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum UserType implements EnumClass<String> {

    JOB_SEEKER("JOB_SEEKER"),
    EMPLOYER("EMPLOYER");

    private final String id;

    UserType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static UserType fromId(String id) {
        for (UserType at : UserType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}