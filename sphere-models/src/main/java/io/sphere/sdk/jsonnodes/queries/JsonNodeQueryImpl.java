package io.sphere.sdk.jsonnodes.queries;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.jsonnodes.expansion.JsonNodeExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

final class JsonNodeQueryImpl extends MetaModelQueryDslImpl<JsonNode, JsonNodeQuery, JsonNodeQueryModel, JsonNodeExpansionModel<JsonNode>> implements JsonNodeQuery {
    JsonNodeQueryImpl(final String path){
        super(path, JsonNodeQuery.resultTypeReference(), JsonNodeQueryModel.of(), JsonNodeExpansionModel.of(), JsonNodeQueryImpl::new);
    }

    private JsonNodeQueryImpl(final MetaModelQueryDslBuilder<JsonNode, JsonNodeQuery, JsonNodeQueryModel, JsonNodeExpansionModel<JsonNode>> builder) {
        super(builder);
    }
}
