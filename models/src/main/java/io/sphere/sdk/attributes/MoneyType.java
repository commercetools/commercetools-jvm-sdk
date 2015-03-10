package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MoneyType extends AttributeTypeBase {
    @JsonIgnore
    private MoneyType() {}

    @JsonCreator
    public static MoneyType of() {
        return new MoneyType();
    }
}
