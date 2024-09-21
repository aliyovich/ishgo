package com.changemakers.ishgo;


import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Author: abdul
 * Since: 9/21/2024 3:20 PM
 */
public class $ {
    public static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    public static boolean isEmpty(String value) {
        if (value == null) return true;
        return value.trim().isEmpty();
    }

    public static boolean isEmpty(Object value) {
        if (value == null) return true;
        if (value instanceof String)
            return ((String) value).trim().isEmpty();
        if (value instanceof Collection)
            return ((Collection<?>) value).isEmpty();
        if (value instanceof Map)
            return ((Map<?, ?>) value).isEmpty();
        if (value instanceof Object[]) {
            return ((Object[]) value).length == 0;
        }
        return false;
    }

    public static boolean isEmail(String email) {
        if (email == null) return false;
        Pattern pat = Pattern.compile(EMAIL_REGEX);
        return pat.matcher(email).matches();
    }

    public static boolean isURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean or(Object object, Object... conditions) {
        for (Object cond : conditions) {
            if (Objects.equals(object, cond)) {
                return true;
            }
        }
        return false;
    }

    public static String dateFormat(TemporalAccessor dateTime, String pattern) {
        try {
            return DateTimeFormatter.ofPattern(pattern).format(dateTime);
        } catch (Exception e) {
            return "";
        }
    }
}
