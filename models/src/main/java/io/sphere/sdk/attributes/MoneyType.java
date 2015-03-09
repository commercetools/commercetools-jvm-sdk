package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MoneyType extends AttributeTypeBase {
    private static final MoneyType CACHED_INSTANCE = new MoneyType();

    @JsonIgnore
    private MoneyType() {}

    @JsonCreator
    public static MoneyType of() {
        return CACHED_INSTANCE;
    }
}
