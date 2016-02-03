package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import javax.annotation.Nullable;
import java.util.List;

public final class PaymentStatusExpansionModel<T> extends ExpansionModel<T> {
    PaymentStatusExpansionModel(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ExpansionPathContainer<T> state() {
        return expansionPath("state");
    }
}
