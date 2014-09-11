package io.sphere.sdk.test;

import io.sphere.sdk.models.DefaultModel;
import org.fest.assertions.GenericAssert;

public class DefaultModelAssert extends GenericAssert<DefaultModelAssert, DefaultModel<?>> {
    public DefaultModelAssert(final DefaultModel<?> actual) {
        super(DefaultModelAssert.class, actual);
    }

    public static DefaultModelAssert assertThat(final DefaultModel<?> actual) {
        return new DefaultModelAssert(actual);
    }

    public DefaultModelAssert hasSameIdAs(final DefaultModel<?> other) {
        if (!actual.getId().equals(other.getId())) {
            failIfCustomMessageIsSet();
            throw failure(String.format(String.format("ID of %s is not equal to the ID of %s.", actual, other)));
        }
        return this;
    }
}
