package io.sphere.sdk.customobjects.migrations.version2;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;

public class Uvw {
    private final Foo foo;
    private final String anotherField;

    public Uvw(final Foo foo, final String anotherField) {
        this.foo = foo;
        this.anotherField = anotherField;
    }

    public Foo getFoo() {
        return foo;
    }

    public String getAnotherField() {
        return anotherField;
    }

    public static TypeReference<CustomObject<Uvw>> customObjectTypeReference(){
        return new TypeReference<CustomObject<Uvw>>(){
            @Override
            public String toString() {
                return "TypeReference<CustomObject<Uvw>>";
            }
        };
    }

    public static class Foo {
        private final String a;
        private final String b;

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
}
