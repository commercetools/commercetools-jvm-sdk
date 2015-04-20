package io.sphere.sdk.customobjects.migrations.version3;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UvwSchemaVersion2 implements Uvw {
    private final Foo foo;
    private final String anotherField;
    private final String schemaVersion = "2";

    @JsonCreator
    public UvwSchemaVersion2(final Foo foo, final String anotherField) {
        this.foo = foo;
        this.anotherField = anotherField;
    }

    public Foo getFoo() {
        return foo;
    }

    public String getAnotherField() {
        return anotherField;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }
}
