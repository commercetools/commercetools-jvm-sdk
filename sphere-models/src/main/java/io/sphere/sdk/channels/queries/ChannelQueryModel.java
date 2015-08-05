package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public class ChannelQueryModel extends ResourceQueryModelImpl<Channel> {
    private ChannelQueryModel(final QueryModel<Channel> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static ChannelQueryModel of() {
        return new ChannelQueryModel(null, null);
    }

    public StringQuerySortingModel<Channel> key() {
        return stringModel("key");
    }
}
