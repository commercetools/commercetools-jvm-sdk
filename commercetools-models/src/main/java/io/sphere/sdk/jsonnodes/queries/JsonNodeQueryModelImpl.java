package io.sphere.sdk.jsonnodes.queries;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.ResourceQueryModelImpl;

final class JsonNodeQueryModelImpl extends ResourceQueryModelImpl<JsonNode> implements JsonNodeQueryModel {
    protected JsonNodeQueryModelImpl() {
        super(null, null);
    }

    @Override
    public QueryPredicate<JsonNode> predicate(final String predicateAsString) {
        return QueryPredicate.of(predicateAsString);
    }
}
