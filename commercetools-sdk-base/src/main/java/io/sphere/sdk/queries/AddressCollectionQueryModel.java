package io.sphere.sdk.queries;

import io.sphere.sdk.queries.AddressQueryModel;
import io.sphere.sdk.queries.CollectionQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryPredicate;

public interface AddressCollectionQueryModel<T> extends QueryModel<T>, CollectionQueryModel<T> {

    @Override
    QueryPredicate<T> isEmpty();

    @Override
    QueryPredicate<T> isNotEmpty();

    AddressQueryModel<T> address();

}
