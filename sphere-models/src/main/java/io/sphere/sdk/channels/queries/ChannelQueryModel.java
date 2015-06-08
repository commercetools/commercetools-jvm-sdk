package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

public class ChannelQueryModel extends DefaultModelQueryModelImpl<Channel> {
    private ChannelQueryModel(final Optional<? extends QueryModel<Channel>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static ChannelQueryModel of() {
        return new ChannelQueryModel(Optional.empty(), Optional.<String>empty());
    }

    public StringQuerySortingModel<Channel> key() {
        return new StringQuerySortingModel<>(Optional.of(this), "key");
    }
}
