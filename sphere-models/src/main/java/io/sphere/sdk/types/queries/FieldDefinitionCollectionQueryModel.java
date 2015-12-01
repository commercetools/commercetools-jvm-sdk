package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.CollectionQueryModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.StringQuerySortingModel;

public interface FieldDefinitionCollectionQueryModel<T> extends CollectionQueryModel<T> {
    @Override
    QueryPredicate<T> isEmpty();

    @Override
    QueryPredicate<T> isNotEmpty();

    StringQuerySortingModel<T> name();

    FieldTypeQueryModel<T> type();
}
