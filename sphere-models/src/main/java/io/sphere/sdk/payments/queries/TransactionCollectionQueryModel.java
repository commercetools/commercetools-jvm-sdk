package io.sphere.sdk.payments.queries;

import io.sphere.sdk.payments.TransactionType;
import io.sphere.sdk.queries.*;

public interface TransactionCollectionQueryModel<T> extends CollectionQueryModel<T> {

    TimestampSortingModel<T> timestamp();

    SphereEnumerationQueryModel<T, TransactionType> type();

    MoneyQueryModel<T> amount();

    StringQuerySortingModel<T> interactionId();
}
