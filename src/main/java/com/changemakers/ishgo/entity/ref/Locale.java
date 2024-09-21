package com.changemakers.ishgo.entity.ref;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;


public enum Locale implements EnumClass<String> {

    EN("en"),
    RU("ru"),
    UZ("uz");

    private final String id;

    Locale(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Locale fromId(String id) {
        for (Locale at : Locale.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}