package com.example.myspringproject.model;

import java.util.Locale;

public enum Type {
    ADD,
    DELETE,
    EDIT;

    public static Type fromString(String val){
        String v = val.toUpperCase(Locale.ROOT);
        if ("ADD".equals(v)) return Type.ADD;
        if ("EDIT".equals(v)) return Type.EDIT;
        if ("DELETE".equals(v)) return Type.DELETE;
        return null;
    }
}
