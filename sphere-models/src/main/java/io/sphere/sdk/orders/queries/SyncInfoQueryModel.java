package io.sphere.sdk.orders.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public class SyncInfoQueryModel<T> extends QueryModelImpl<T> {
    SyncInfoQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ReferenceQueryModel<T, Channel> channel() {
        return referenceModel("channel");
    }

    public StringQuerySortingModel<T> externalId() {
        return stringModel("externalId");
    }

    public final TimestampSortingModel<T> syncedAt() {
        return new TimestampSortingModel<>(Optional.of(this), "syncedAt");
    }
}
