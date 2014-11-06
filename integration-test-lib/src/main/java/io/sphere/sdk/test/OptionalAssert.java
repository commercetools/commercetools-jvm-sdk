package io.sphere.sdk.test;

import org.fest.assertions.GenericAssert;

import java.util.Optional;

import static java.lang.String.format;

public class OptionalAssert extends GenericAssert<OptionalAssert, Optional<?>> {
    protected OptionalAssert(final Optional<?> actual) {
        super(OptionalAssert.class, actual);
    }

    public static OptionalAssert assertThat(final Optional<?> actual) {
        return new OptionalAssert(actual);
    }

    public OptionalAssert isPresentAs(final Object thing) {
        if (!actual.isPresent()) {
            failIfCustomMessageIsSet();
            throw failure(format("The optional is empty."));
        }
        if (!actual.get().equals(thing)) {
            failIfCustomMessageIsSet();
            throw failure(format("%s is not %s.", actual, Optional.ofNullable(thing)));
        }
        return this;
    }

    public OptionalAssert isPresent() {
        if (!actual.isPresent()) {
            failIfCustomMessageIsSet();
            throw failure(format("The optional is empty."));
        }
        return this;
    }

    public OptionalAssert isAbsent() {
        if (actual.isPresent()) {
            failIfCustomMessageIsSet();
            throw failure(format("The optional is filled: %s.", actual));
        }
        return this;
    }

    public OptionalAssert isEmpty() {
        return isAbsent();
    }
}
