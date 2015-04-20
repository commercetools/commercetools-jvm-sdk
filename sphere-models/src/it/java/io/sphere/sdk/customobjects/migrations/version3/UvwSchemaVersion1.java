package io.sphere.sdk.customobjects.migrations.version3;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UvwSchemaVersion1 extends UvwSchemaVersion2 implements Uvw {

    //the constructor parameter contains the the fields of the first version
    //so foo is a String
    @JsonCreator
    public UvwSchemaVersion1(final String foo, final String anotherField) {
        super(toFooObject(foo), anotherField);
    }

    private static Foo toFooObject(final String foo) {
        final String[] strings = foo.split("&", 2);
        final String a = strings[0];
        final String b = strings[1];
        return new Foo(a, b);
    }
}
