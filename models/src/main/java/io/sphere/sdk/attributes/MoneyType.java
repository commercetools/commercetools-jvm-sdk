package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MoneyType extends AttributeTypeBase {
    private MoneyType() {}

    @JsonIgnore
    public static MoneyType of() {
        return new MoneyType();
    }
}
