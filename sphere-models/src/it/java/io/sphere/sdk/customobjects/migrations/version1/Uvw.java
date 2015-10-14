package io.sphere.sdk.customobjects.migrations.version1;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Uvw {
    private final String foo;
    private final String anotherField;

    @JsonCreator
    public Uvw(final String foo, final String anotherField) {
        this.foo = foo;
        this.anotherField = anotherField;
    }

    public String getFoo() {
        return foo;
    }

    public String getAnotherField() {
        return anotherField;
    }
}
