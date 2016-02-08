package io.sphere.sdk.customobjects.migrations.version3;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public class Foo extends Base {
    private final String a;
    private final String b;

    @JsonCreator
    public Foo(final String a, final String b) {
        this.a = a;
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }
}
