package dev.richard.soulnotes.entities;

import java.util.HashMap;
import java.util.Map;

public enum Roles {
    BASIC(1),
    PREMIUM(2),
    ADMIN(3);
    public final int Value;
    public static final int size = values().length;

    Roles(int value) {
        Value = value;
    }
    // map the values of the role in the db to a friendlier representation
    static final Map<Integer, Roles> ROLES_MAP = new HashMap<>();

    static {
        for (Roles r : Roles.values())
            ROLES_MAP.put(r.Value, r);
    }
    public static Roles from(int value) {
        return ROLES_MAP.get(value);
    }

    public static Map<Integer, Roles> getAllRoles() {
        return ROLES_MAP;
    }
}
