package io.sphere.sdk.customobjects.migrations.version2;

import com.fasterxml.jackson.annotation.JsonCreator;
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
}
