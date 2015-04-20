package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class TextType extends AttributeTypeBase {
    private TextType() {}

    @JsonIgnore
    public static TextType of() {
        return new TextType();
    }
}
