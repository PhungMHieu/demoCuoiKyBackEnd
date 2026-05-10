package com.example.democuoikybackend.adapter;

/**
 * @deprecated Use {@link ConverterFactory} instead.
 */
@Deprecated
public final class DataConverterFactory {
    private DataConverterFactory() {
    }

    public static DataConverter from(ConverterType type) {
        return ConverterFactory.from(type);
    }

    public static DataConverter fromName(String name) {
        return ConverterFactory.fromName(name);
    }
}
