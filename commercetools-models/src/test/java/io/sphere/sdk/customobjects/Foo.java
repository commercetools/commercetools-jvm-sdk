package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

/**
 A demo class for a value of a custom object
 */
public class Foo extends Base {

    /** this custom object stores a long */
    private final Long baz;
    /** this custom object stores also a String*/
    private final String bar;

    @JsonCreator
    public Foo(final String bar, final Long baz) {
        this.bar = bar;
        this.baz = baz;
    }

    public String getBar() {
        return bar;
    }

    public long getBaz() {
        return baz;
    }
}
