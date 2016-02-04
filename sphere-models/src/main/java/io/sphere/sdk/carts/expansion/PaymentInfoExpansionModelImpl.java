package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpandedModel;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;

import javax.annotation.Nullable;
import java.util.List;

final class PaymentInfoExpansionModelImpl<T> extends ExpandedModel<T> implements PaymentInfoExpansionModel<T> {
    public PaymentInfoExpansionModelImpl(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    @Override
    public PaymentExpansionModel<T> payments() {
        return PaymentExpansionModel.of(buildPathExpression(), "payments[*]");
    }
}
