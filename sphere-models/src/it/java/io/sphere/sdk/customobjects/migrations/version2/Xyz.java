package io.sphere.sdk.customobjects.migrations.version2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public class Xyz extends Base {
    private final String foo;
    @Nullable
    private final String bar;

    @JsonCreator
    public Xyz(final String foo, @Nullable final String bar) {
        this.foo = foo;
        this.bar = bar;
    }

    public String getFoo() {
        return foo;
    }

    @Nullable
    public String getBar() {
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
