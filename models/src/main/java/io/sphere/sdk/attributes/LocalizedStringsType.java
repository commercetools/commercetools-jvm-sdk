package io.sphere.sdk.attributes;

public final class LocalizedStringsType extends AttributeTypeBase {
    private static final LocalizedStringsType CACHED_INSTANCE = new LocalizedStringsType();

    private LocalizedStringsType() {}

    public static LocalizedStringsType of() {
        return CACHED_INSTANCE;
    }
}
