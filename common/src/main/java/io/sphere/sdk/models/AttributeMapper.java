package io.sphere.sdk.models;

/**
 *
 * @param <T> the result type of the attribute, e.g., {@link io.sphere.sdk.models.LocalizedString}
 *
 */
public interface AttributeMapper<T> {
    public static AttributeMapper<Double> ofDouble() {
        return null;
    }

    public static AttributeMapper<LocalizedString> ofLocalizedString() {
        return null;
    }
}
