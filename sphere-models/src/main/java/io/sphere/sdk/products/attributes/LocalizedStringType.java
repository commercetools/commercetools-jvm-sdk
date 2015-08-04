package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class LocalizedStringType extends AttributeTypeBase {
    private LocalizedStringType() {}

    @JsonIgnore
    public static LocalizedStringType of() {
        return new LocalizedStringType();
    }
}
