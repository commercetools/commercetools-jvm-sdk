package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestEntity {
    private final String foo;

    @JsonCreator
    public TestEntity(@JsonProperty("foo") final String foo) {
        this.foo = foo;
    }

    public TestEntity() {
        this("default");
    }

    public String getFoo() {
        return foo;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "foo='" + foo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestEntity that = (TestEntity) o;

        if (!foo.equals(that.foo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return foo.hashCode();
    }
}
