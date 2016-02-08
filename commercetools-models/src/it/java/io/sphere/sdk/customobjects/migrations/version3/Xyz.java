package io.sphere.sdk.customobjects.migrations.version3;

import com.fasterxml.jackson.annotation.JsonCreator;
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
}
