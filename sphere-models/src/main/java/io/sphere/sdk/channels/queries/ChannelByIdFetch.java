package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.ByIdFetchImpl;

public class ChannelByIdFetch extends ByIdFetchImpl<Channel> {
    private ChannelByIdFetch(final String id) {
        super(id, ChannelsEndpoint.ENDPOINT);
    }

    public static ChannelByIdFetch of(final String id) {
        return new ChannelByIdFetch(id);
    }
}
