package io.sphere.sdk.customobjects.migrations.version1;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public class Xyz extends Base {
    private final String foo;

    @JsonCreator
    public Xyz(final String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return foo;
    }
}
