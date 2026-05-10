package com.example.democuoikybackend.adapter;

import java.util.Locale;

public enum ConverterType {
    GSON,
    JACKSON,
    JAXB;

    public static ConverterType from(String name) {
        String n = name == null ? "" : name.trim().toLowerCase(Locale.ROOT);
        return switch (n) {
            case "gson", "json-gson" -> GSON;
            case "jackson", "json-jackson", "json" -> JACKSON;
            case "jaxb", "xml", "jax" -> JAXB;
            default -> GSON;
        };
    }

    public boolean isXml() {
        return this == JAXB;
    }
}

