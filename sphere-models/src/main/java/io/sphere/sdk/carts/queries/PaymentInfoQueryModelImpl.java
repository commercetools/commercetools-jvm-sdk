package io.sphere.sdk.carts.queries;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.ReferenceCollectionQueryModel;

final class PaymentInfoQueryModelImpl<T> extends QueryModelImpl<T> implements PaymentInfoQueryModel<T> {
    public PaymentInfoQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }

    @Override
    public ReferenceCollectionQueryModel<T, Payment> payments() {
        return referenceCollectionModel("payments");
    }
}
