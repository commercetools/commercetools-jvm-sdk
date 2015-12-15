package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathsHolder;

import javax.annotation.Nullable;
import java.util.List;

public class PaymentInfoExpansionModel<T> extends ExpansionModel<T> {
    public PaymentInfoExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ExpansionPathsHolder<T> payments() {
        return expansionPath("payments[*]");
    }
}
