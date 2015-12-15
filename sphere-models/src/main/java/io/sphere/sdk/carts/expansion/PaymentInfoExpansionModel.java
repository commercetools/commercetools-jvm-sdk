package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ReferenceExpansionSupport;

import javax.annotation.Nullable;
import java.util.List;

public class PaymentInfoExpansionModel<T> extends ExpansionModel<T> {
    public PaymentInfoExpansionModel(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ReferenceExpansionSupport<T> payments() {
        return expansionPath("payments[*]");
    }
}
