package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ReferenceExpansionSupport;

import javax.annotation.Nullable;
import java.util.List;

public class PaymentStatusExpansionModel<T> extends ExpansionModel<T> {
    PaymentStatusExpansionModel(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ReferenceExpansionSupport<T> state() {
        return expansionPath("state");
    }
}
