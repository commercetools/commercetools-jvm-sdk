package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

import javax.annotation.Nullable;

public class PaymentInfoExpansionModel<T> extends ExpansionModel<T> {
    public PaymentInfoExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ExpansionPath<T> payments() {
        return expansionPath("payments[*]");
    }
}
