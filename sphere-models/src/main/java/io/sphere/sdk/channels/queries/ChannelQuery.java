package io.sphere.sdk.channels.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;

/**
 {@doc.gen summary channels}
 */
public class ChannelQuery extends DefaultModelQuery<Channel> {
    private ChannelQuery() {
        super(ChannelsEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<Channel>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Channel>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Channel>>";
            }
        };
    }

    public QueryDsl<Channel> byKey(final String key) {
        return withPredicate(model().key().is(key));
    }

    public static ChannelQueryModel model() {
        return ChannelQueryModel.get();
    }

    public static ChannelQuery of() {
        return new ChannelQuery();
    }
}
