package io.sphere.sdk.carts.queries;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.queries.OptionalQueryModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.ReferenceCollectionQueryModel;

public interface PaymentInfoQueryModel<T> extends OptionalQueryModel<T> {
    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isPresent();

    ReferenceCollectionQueryModel<T, Payment> payments();
}
