package com.example.democuoikybackend.adapter;

/**
 * Factory for creating DataConverter implementations based on ConverterType.
 */
public final class ConverterFactory {
    private ConverterFactory() {
    }

    public static DataConverter from(ConverterType type) {
        ConverterType t = (type == null) ? ConverterType.GSON : type;
        return switch (t) {
            case GSON -> new GSonAdapter();
            case JACKSON -> new JacksonAdapter();
            case JAXB -> new JaxAdapter();
        };
    }

    /** Backward compatible entry point (kept for current usages). */
    public static DataConverter fromName(String name) {
        return from(ConverterType.from(name));
    }
}

