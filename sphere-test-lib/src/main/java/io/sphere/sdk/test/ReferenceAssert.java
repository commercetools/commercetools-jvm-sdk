package io.sphere.sdk.test;

import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import static java.lang.String.format;

public class ReferenceAssert extends AbstractAssert<ReferenceAssert, Reference<?>> {

    protected ReferenceAssert(final Reference<?> actual) {
        super(actual, ReferenceAssert.class);
    }

    public static ReferenceAssert assertThat(final Reference<?> actual) {
        return new ReferenceAssert(actual);
    }

    public ReferenceAssert hasId(final String id) {
        Assertions.assertThat(actual.getId()).isEqualTo(id);
        return this;
    }

    public ReferenceAssert hasAnExpanded(final DefaultModel<?> model) {
        checkIsExpanded();
        if (!actual.getObj().equals(model)) {
            failWithMessage(format("%s does not contain an expanded %s.", actual, model));
        }
        return this;
    }

    public void checkIsExpanded() {
        if (actual.getObj() != null) {
            failWithMessage(format("The reference %s is not expanded.", actual));
        }
    }

    public ReferenceAssert isExpanded() {
        checkIsExpanded();
        return this;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public ReferenceAssert references(final Referenceable counterpart) {
        if (!actual.referencesSameResource(counterpart)) {
            failWithMessage(format("%s does not reference the same as %s.", actual, counterpart));
        }
        return this;
    }
}
