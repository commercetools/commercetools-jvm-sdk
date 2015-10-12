package io.sphere.sdk.payments.queries;

import io.sphere.sdk.queries.CollectionQueryModel;
import io.sphere.sdk.queries.MoneyQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.queries.TimestampSortingModel;

public interface TransactionCollectionQueryModel<T> extends CollectionQueryModel<T> {

    TimestampSortingModel<T> timestamp();

//    SphereEnumerationQueryModel<T, TransactionType> type();

    MoneyQueryModel<T> amount();

    StringQuerySortingModel<T> interactionId();
}
