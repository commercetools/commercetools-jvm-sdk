package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MoneyType extends FieldTypeBase {

    @JsonCreator
    private MoneyType() {
    }

    @JsonIgnore
    public static MoneyType of() {
        return new MoneyType();
    }
}
