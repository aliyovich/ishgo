package com.changemakers.ishgo.entity.candidate;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum ApplyStatus implements EnumClass<String> {

    NEW("NEW"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED");

    private final String id;

    ApplyStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplyStatus fromId(String id) {
        for (ApplyStatus at : ApplyStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}