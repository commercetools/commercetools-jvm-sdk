package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public final class LocalizedStringsType extends AttributeTypeBase {
    private LocalizedStringsType() {}

    @JsonIgnore
    public static LocalizedStringsType of() {
        return new LocalizedStringsType();
    }
}
