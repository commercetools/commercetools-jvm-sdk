package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;

import javax.annotation.Nullable;
import java.util.List;

final class PaymentInfoExpansionModelImpl<T> extends ExpansionModelImpl<T> implements PaymentInfoExpansionModel<T> {
    public PaymentInfoExpansionModelImpl(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    @Override
    public PaymentExpansionModel<T> payments() {
        return PaymentExpansionModel.of(buildPathExpression(), "payments[*]");
    }
}
