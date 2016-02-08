package io.sphere.sdk.payments.queries;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

final class PaymentQueryImpl extends MetaModelQueryDslImpl<Payment, PaymentQuery, PaymentQueryModel, PaymentExpansionModel<Payment>> implements PaymentQuery {
    PaymentQueryImpl(){
        super(PaymentEndpoint.ENDPOINT.endpoint(), PaymentQuery.resultTypeReference(), PaymentQueryModel.of(), PaymentExpansionModel.of(), PaymentQueryImpl::new);
    }

    private PaymentQueryImpl(final MetaModelQueryDslBuilder<Payment, PaymentQuery, PaymentQueryModel, PaymentExpansionModel<Payment>> builder) {
        super(builder);
    }
}