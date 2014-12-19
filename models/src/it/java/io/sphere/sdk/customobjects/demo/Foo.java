package io.sphere.sdk.customobjects.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.models.Base;

public class Foo extends Base {

    private final String bar;
    private final long baz;

    public Foo(final String bar, final long baz) {
        this.bar = bar;
        this.baz = baz;
    }

    public String getBar() {
        return bar;
    }

    public long getBaz() {
        return baz;
    }

    public static TypeReference<CustomObject<Foo>> customObjectTypeReference() {
        return new TypeReference<CustomObject<Foo>>(){
            @Override
            public String toString() {
                return "TypeReference<CustomObject<" + Foo.class.getSimpleName() + ">>";
            }
        };
    }
}
