package io.sphere.sdk.customobjects.migrations.version1;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;

public class Uvw {
    private final String foo;
    private final String anotherField;

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

    public static TypeReference<CustomObject<Uvw>> customObjectTypeReference(){
        return new TypeReference<CustomObject<Uvw>>(){
            @Override
            public String toString() {
                return "TypeReference<CustomObject<Uvw>>";
            }
        };
    }
}
