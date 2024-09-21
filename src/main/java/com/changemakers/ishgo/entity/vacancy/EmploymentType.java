package com.changemakers.ishgo.entity.vacancy;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum EmploymentType implements EnumClass<String> {

    FULL_TIME("FULL_TIME"),
    PART_TIME("PART_TIME"),
    INTERN("INTERN"),
    TEMPORARY("TEMPORARY"),
    REMOTE("REMOTE"),
    FREELANCE("FREELANCE");

    private final String id;

    EmploymentType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EmploymentType fromId(String id) {
        for (EmploymentType at : EmploymentType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}