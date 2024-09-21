package com.changemakers.ishgo.entity.company;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;


public enum CompanyStatus implements EnumClass<String> {

    ACTIVE("ACTIVE"),
    PENDING("PENDING"),
    INACTIVE("INACTIVE");

    private final String id;

    CompanyStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CompanyStatus fromId(String id) {
        for (CompanyStatus at : CompanyStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}