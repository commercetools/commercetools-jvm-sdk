package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.QueryToFetchAdapter;

public class FetchChannelByKey extends QueryToFetchAdapter<Channel> {

    public FetchChannelByKey(final String key) {
        super(ChannelQuery.resultTypeReference(), new ChannelQuery().byKey(key));
    }
}
