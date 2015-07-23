package io.sphere.sdk.channels.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

/**
 {@doc.gen summary channels}
 */
public interface ChannelQuery extends MetaModelQueryDsl<Channel, ChannelQuery, ChannelQueryModel, ChannelExpansionModel<Channel>> {
    static TypeReference<PagedQueryResult<Channel>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Channel>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Channel>>";
            }
        };
    }

    default ChannelQuery byKey(final String key) {
        return withPredicates(m -> m.key().is(key));
    }

    static ChannelQuery of() {
        return new ChannelQueryImpl();
    }
}
