package io.sphere.sdk.customobjects.migrations.version1;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.models.Base;

public class Xyz extends Base {
    private final String foo;

    public Xyz(final String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return foo;
    }

    public static TypeReference<CustomObject<Xyz>> customObjectTypeReference() {
        return new TypeReference<CustomObject<Xyz>>() {
            @Override
            public String toString() {
                return "TypeReference<CustomObject<Xyz>>";
            }
        };
    }
}
