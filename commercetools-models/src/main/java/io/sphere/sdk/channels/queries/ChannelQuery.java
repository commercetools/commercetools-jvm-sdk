package io.sphere.sdk.channels.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

/**
 * Queries channels.
 *
 {@doc.gen summary channels}

 {@include.example io.sphere.sdk.channels.queries.ChannelQueryIntegrationTest#execution()}
 */
public interface ChannelQuery extends MetaModelQueryDsl<Channel, ChannelQuery, ChannelQueryModel, ChannelExpansionModel<Channel>> {
    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
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
