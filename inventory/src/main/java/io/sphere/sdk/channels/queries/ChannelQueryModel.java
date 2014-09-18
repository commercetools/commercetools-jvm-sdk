package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

public class ChannelQueryModel extends QueryModelImpl<Channel> {
    private ChannelQueryModel(final Optional<? extends QueryModel<Channel>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static ChannelQueryModel get() {
        return new ChannelQueryModel(Optional.<QueryModel<Channel>>empty(), Optional.<String>empty());
    }

    public StringQuerySortingModel<Channel> key() {
        return new StringQuerySortingModel<>(Optional.of(this), "key");
    }
}
