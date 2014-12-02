package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.QueryToFetchAdapter;

public class ChannelFetchByKey extends QueryToFetchAdapter<Channel> {

    private ChannelFetchByKey(final String key) {
        super(ChannelQuery.resultTypeReference(), ChannelQuery.of().byKey(key));
    }

    public static ChannelFetchByKey of(final String key) {
        return new ChannelFetchByKey(key);
    }
}
