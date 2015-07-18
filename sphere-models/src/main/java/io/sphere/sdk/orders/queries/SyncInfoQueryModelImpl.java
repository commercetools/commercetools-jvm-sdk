package io.sphere.sdk.orders.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.*;

import java.util.Optional;

final class SyncInfoQueryModelImpl<T> extends QueryModelImpl<T> implements SyncInfoQueryModel<T> {
    SyncInfoQueryModelImpl(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
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
        return new TimestampSortingModel<>(Optional.of(this), "syncedAt");
    }
}
