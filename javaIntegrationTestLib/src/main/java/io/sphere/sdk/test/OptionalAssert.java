package io.sphere.sdk.test;

import org.fest.assertions.Assertions;
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
        Assertions.assertThat(actual.isPresent()).overridingErrorMessage(customErrorMessage()).isTrue();
        return this;
    }
}
