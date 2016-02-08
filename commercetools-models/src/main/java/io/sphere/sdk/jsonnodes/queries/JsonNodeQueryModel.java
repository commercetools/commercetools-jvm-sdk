package io.sphere.sdk.jsonnodes.queries;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.ResourceQueryModel;

public interface JsonNodeQueryModel extends ResourceQueryModel<JsonNode> {
    static JsonNodeQueryModel of() {
        return new JsonNodeQueryModelImpl();
    }

    QueryPredicate<JsonNode> predicate(String predicateAsString);
}
