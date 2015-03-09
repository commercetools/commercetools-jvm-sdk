package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public final class TextType extends AttributeTypeBase {
    private static final TextType CACHED_INSTANCE = new TextType();

    @JsonIgnore
    private TextType() {}

    @JsonCreator
    public static TextType of() {
        return CACHED_INSTANCE;
    }
}
