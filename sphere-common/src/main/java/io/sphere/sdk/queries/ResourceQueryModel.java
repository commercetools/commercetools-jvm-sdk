package io.sphere.sdk.queries;

import io.sphere.sdk.models.Identifiable;

public interface ResourceQueryModel<T> {
    StringQuerySortingModel<T> id();

    QueryPredicate<T> is(final Identifiable<T> identifiable);

    TimestampSortingModel<T> createdAt();

    TimestampSortingModel<T> lastModifiedAt();
}
