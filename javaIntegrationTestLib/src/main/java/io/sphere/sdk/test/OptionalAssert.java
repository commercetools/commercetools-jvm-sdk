package io.sphere.sdk.test;

import org.fest.assertions.GenericAssert;

import java.util.Optional;

public class OptionalAssert extends GenericAssert<OptionalAssert, Optional<?>> {
    protected OptionalAssert(final Optional<?> actual) {
        super(OptionalAssert.class, actual);
    }

    public static OptionalAssert assertThat(final Optional<?> actual) {
        return new OptionalAssert(actual);
    }

    public OptionalAssert isPresent() {
        if (!actual.isPresent()) {
            failIfCustomMessageIsSet();
            throw failure(String.format("The optional is empty."));
        }
        return this;
    }
}
