package io.sphere.sdk.customobjects.migrations.version3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public class Xyz extends Base {
    @Nullable
    private final String bar;

    @JsonCreator
    public Xyz(@Nullable final String bar) {
        this.bar = bar;
    }

    @Nullable
    public String getBar() {
        return bar;
    }

    public static TypeReference<Xyz> typeReference() {
        return new TypeReference<Xyz>() {
            @Override
            public String toString() {
                return "TypeReference<Xyz>";
            }
        };
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
