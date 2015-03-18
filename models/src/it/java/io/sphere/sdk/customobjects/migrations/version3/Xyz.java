package io.sphere.sdk.customobjects.migrations.version3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.models.Base;

import java.util.Optional;

public class Xyz extends Base {
    private final Optional<String> bar;

    @JsonCreator
    public Xyz(final Optional<String> bar) {
        this.bar = bar;
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
