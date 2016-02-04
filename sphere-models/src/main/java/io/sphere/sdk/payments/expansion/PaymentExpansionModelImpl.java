package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpandedModel;

import java.util.List;

final class PaymentExpansionModelImpl<T> extends ExpandedModel<T> implements PaymentExpansionModel<T> {
    PaymentExpansionModelImpl() {
        super();
    }

    public PaymentExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public CustomerExpansionModel<T> customer() {
        return CustomerExpansionModel.of(buildPathExpression(), "customer");
    }

    @Override
    public PaymentStatusExpansionModel<T> paymentStatus() {
        return new PaymentStatusExpansionModelImpl<>(buildPathExpression(), "paymentStatus");
    }
}
