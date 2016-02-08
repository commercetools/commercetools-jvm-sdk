package io.sphere.sdk.customobjects.migrations.version2;

import com.fasterxml.jackson.annotation.JsonCreator;

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

    public static class Foo {
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
}
