package io.sphere.sdk.customobjects.migrations.version2;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.models.Base;

import java.util.Optional;

public class Xyz extends Base {
    private final String foo;
    private final Optional<String> bar;

    public Xyz(final String foo, final Optional<String> bar) {
        this.foo = foo;
        this.bar = bar;
    }

    public String getFoo() {
        return foo;
    }

    public Optional<String> getBar() {
        return bar;
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
