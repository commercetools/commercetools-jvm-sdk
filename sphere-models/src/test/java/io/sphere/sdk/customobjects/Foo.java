package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.PagedQueryResult;

/**
 A demo class for a value of a custom object
 */
public class Foo extends Base {

    /** this custom object stores a long */
    private final long baz;
    /** this custom object stores also a String*/
    private final String bar;

    @JsonCreator
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

    public static TypeReference<Foo> typeReference() {
        return new TypeReference<Foo>(){
            @Override
            public String toString() {
                return "TypeReference<" + Foo.class.getSimpleName() + ">";
            }
        };
    }

    public static TypeReference<CustomObject<Foo>> customObjectTypeReference() {
        return new TypeReference<CustomObject<Foo>>(){
            @Override
            public String toString() {
                return "TypeReference<CustomObject<" + Foo.class.getSimpleName() + ">>";
            }
        };
    }

    public static TypeReference<PagedQueryResult<CustomObject<Foo>>> pagedQueryResultTypeReference() {
        return new TypeReference<PagedQueryResult<CustomObject<Foo>>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<CustomObject<" + Foo.class.getSimpleName() + ">>>";
            }
        };
    }
}
