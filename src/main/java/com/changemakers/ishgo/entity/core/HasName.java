package com.changemakers.ishgo.entity.core;


/**
 * Author: abdul
 * Since: 9/20/2024 8:04 PM
 */
public interface HasName {
    String NAME = "name";

    String getName();

    void setName(String name);

    interface HasShortName {
        String SHORT_NAME = "shortName";

        String getShortName();

        void setShortName(String shortName);
    }
}
