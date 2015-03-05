package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public final class LocalizedStringsType extends AttributeTypeBase {
    private static final LocalizedStringsType CACHED_INSTANCE = new LocalizedStringsType();

    @JsonIgnore
    private LocalizedStringsType() {}

    @JsonCreator
    public static LocalizedStringsType of() {
        return CACHED_INSTANCE;
    }
}
