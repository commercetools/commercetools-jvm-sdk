package io.sphere.sdk.payments.queries;

import io.sphere.sdk.payments.TransactionState;
import io.sphere.sdk.payments.TransactionType;
import io.sphere.sdk.queries.*;

final class TransactionCollectionQueryModelImpl<T> extends QueryModelImpl<T> implements TransactionCollectionQueryModel<T> {
    public TransactionCollectionQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public MoneyQueryModel<T> amount() {
        return moneyModel("amount");
    }

    @Override
    public StringQuerySortingModel<T> interactionId() {
        return stringModel("interactionId");
    }

    @Override
    public TimestampSortingModel<T> timestamp() {
        return timestampSortingModel("timestamp");
    }

    @Override
    public QueryPredicate<T> isEmpty() {
        return isEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> isNotEmpty() {
        return isNotEmptyCollectionQueryPredicate();
    }

    @Override
    public SphereEnumerationQueryModel<T, TransactionType> type() {
        return enumerationQueryModel("type");
    }

    @Override
    public StringQuerySortingModel<T> id() {
        return stringModel("id");
    }

    @Override
    public SphereEnumerationQueryModel<T, TransactionState> state() {
        return enumerationQueryModel("state");
    }
}
