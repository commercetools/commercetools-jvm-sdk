package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

public class ChannelQueryModel<T> extends DefaultModelQueryModelImpl<T> {
    private ChannelQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static ChannelQueryModel<Channel> of() {
        return new ChannelQueryModel<>(Optional.empty(), Optional.<String>empty());
    }

    public StringQuerySortingModel<T> key() {
        return new StringQuerySortingModel<>(Optional.of(this), "key");
    }
}
