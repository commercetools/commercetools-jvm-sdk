package io.sphere.sdk.orders.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.CollectionQueryModel;
import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.queries.TimestampSortingModel;

public interface SyncInfoQueryModel<T> extends CollectionQueryModel<T> {
    ReferenceQueryModel<T, Channel> channel();

    StringQuerySortingModel<T> externalId();

    TimestampSortingModel<T> syncedAt();
}
