package io.sphere.sdk.test;

import io.sphere.sdk.models.DefaultModel;
import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;

public class DefaultModelAssert extends GenericAssert<DefaultModelAssert, DefaultModel<?>> {
    public DefaultModelAssert(final DefaultModel<?> actual) {
        super(DefaultModelAssert.class, actual);
    }

    public static DefaultModelAssert assertThat(final DefaultModel<?> actual) {
        return new DefaultModelAssert(actual);
    }

    public DefaultModelAssert hasSameIdAs(final DefaultModel<?> other) {
        Assertions.assertThat(actual.getId()).
                overridingErrorMessage(String.format("id of %s is not equal to the id of %s", actual, other)).
                isEqualTo(other.getId());
        return this;
    }
}
