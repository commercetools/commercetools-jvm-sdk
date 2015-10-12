package io.sphere.sdk.payments.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.ReferenceOptionalQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.states.State;

final class PaymentStatusQueryModelImpl<T> extends QueryModelImpl<T> implements PaymentStatusQueryModel<T> {
    public PaymentStatusQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);


    }

    @Override
    public StringQuerySortingModel<T> interfaceCode() {
        return stringModel("interfaceCode");
    }

    @Override
    public StringQuerySortingModel<T> interfaceText() {
        return stringModel("interfaceText");
    }

    @Override
    public ReferenceOptionalQueryModel<T, State> state() {
        return referenceOptionalModel("state");
    }
}
