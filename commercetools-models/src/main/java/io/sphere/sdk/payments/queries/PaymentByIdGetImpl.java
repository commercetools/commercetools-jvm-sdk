package io.sphere.sdk.payments.queries;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class PaymentByIdGetImpl extends MetaModelGetDslImpl<Payment, Payment, PaymentByIdGet, PaymentExpansionModel<Payment>> implements PaymentByIdGet {
    PaymentByIdGetImpl(final String id) {
        super(id, PaymentEndpoint.ENDPOINT, PaymentExpansionModel.of(), PaymentByIdGetImpl::new);
    }

    public PaymentByIdGetImpl(final MetaModelGetDslBuilder<Payment, Payment, PaymentByIdGet, PaymentExpansionModel<Payment>> builder) {
        super(builder);
    }
}
