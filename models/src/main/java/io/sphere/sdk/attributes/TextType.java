package io.sphere.sdk.attributes;

public final class TextType extends AttributeTypeBase {
    private static final TextType CACHED_INSTANCE = new TextType();

    private TextType() {}

    public static TextType of() {
        return CACHED_INSTANCE;
    }
}
