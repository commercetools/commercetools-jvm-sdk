package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

import javax.annotation.Nullable;

public class PaymentStatusExpansionModel<T> extends ExpansionModel<T> {
    PaymentStatusExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ExpansionPath<T> state() {
        return expansionPath("state");
    }
}
