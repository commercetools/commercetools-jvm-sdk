package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.QueryToFetchAdapter;

public class ChannelByKeyFetch extends QueryToFetchAdapter<Channel> {

    private ChannelByKeyFetch(final String key) {
        super(ChannelQuery.resultTypeReference(), ChannelQuery.of().byKey(key));
    }

    public static ChannelByKeyFetch of(final String key) {
        return new ChannelByKeyFetch(key);
    }
}
