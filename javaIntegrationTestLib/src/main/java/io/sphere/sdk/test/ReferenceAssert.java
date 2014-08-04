package io.sphere.sdk.test;

import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;
import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;

public class ReferenceAssert extends GenericAssert<ReferenceAssert, Reference<?>> {

    protected ReferenceAssert(final Reference<?> actual) {
        super(ReferenceAssert.class, actual);
    }

    public static ReferenceAssert assertThat(final Reference<?> actual) {
        return new ReferenceAssert(actual);
    }

    public ReferenceAssert hasId(final String id) {
        Assertions.assertThat(actual.getId()).isEqualTo(id);
        return this;
    }

    public ReferenceAssert hasAnExpanded(final DefaultModel<?> model) {
        checkIsPresent();
        Assertions.assertThat(actual.getObj().get()).isEqualTo(model);
        return this;
    }

    public void checkIsPresent() {
        OptionalAssert.assertThat(actual.getObj()).
                overridingErrorMessage(String.format("The reference %s is not expanded.", actual)).isPresent();
    }

    public ReferenceAssert isExpanded() {
        checkIsPresent();
        return this;
    }
}
