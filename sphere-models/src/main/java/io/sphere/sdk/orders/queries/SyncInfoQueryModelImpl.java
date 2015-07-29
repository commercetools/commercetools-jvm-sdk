package io.sphere.sdk.orders.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.*;

final class SyncInfoQueryModelImpl<T> extends QueryModelImpl<T> implements SyncInfoQueryModel<T> {
    SyncInfoQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceQueryModel<T, Channel> channel() {
        return referenceModel("channel");
    }

    @Override
    public StringQuerySortingModel<T> externalId() {
        return stringModel("externalId");
    }

    @Override
    public final TimestampSortingModel<T> syncedAt() {
        return timestampSortingModel("syncedAt");
    }
}
