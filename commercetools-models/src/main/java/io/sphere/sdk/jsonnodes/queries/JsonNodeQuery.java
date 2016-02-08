package io.sphere.sdk.jsonnodes.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.jsonnodes.expansion.JsonNodeExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;

import java.util.List;
import java.util.function.Function;

public interface JsonNodeQuery extends MetaModelQueryDsl<JsonNode, JsonNodeQuery, JsonNodeQueryModel, JsonNodeExpansionModel<JsonNode>> {
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
    static TypeReference<PagedQueryResult<JsonNode>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<JsonNode>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<JsonNode>>";
            }
        };
    }

    static JsonNodeQuery of(final String path) {
        return new JsonNodeQueryImpl(path);
    }

    @Override
    JsonNodeQuery plusPredicates(final Function<JsonNodeQueryModel, QueryPredicate<JsonNode>> jsonNodeQueryModelQueryPredicateFunction);

    @Override
    JsonNodeQuery plusPredicates(final QueryPredicate<JsonNode> queryPredicate);

    @Override
    JsonNodeQuery plusPredicates(final List<QueryPredicate<JsonNode>> queryPredicates);

    @Override
    JsonNodeQuery plusSort(final List<QuerySort<JsonNode>> sort);

    @Override
    JsonNodeQuery plusSort(final QuerySort<JsonNode> sort);

    @Override
    JsonNodeQuery plusSort(final Function<JsonNodeQueryModel, QuerySort<JsonNode>> jsonNodeQueryModelQuerySortFunction);

    @Override
    JsonNodeQuery withPredicates(final Function<JsonNodeQueryModel, QueryPredicate<JsonNode>> jsonNodeQueryModelQueryPredicateFunction);

    @Override
    JsonNodeQuery withPredicates(final QueryPredicate<JsonNode> queryPredicate);

    @Override
    JsonNodeQuery withPredicates(final List<QueryPredicate<JsonNode>> queryPredicates);

    @Override
    JsonNodeQuery withSort(final List<QuerySort<JsonNode>> sort);

    @Override
    JsonNodeQuery withSort(final QuerySort<JsonNode> sort);

    @Override
    JsonNodeQuery withSort(final Function<JsonNodeQueryModel, QuerySort<JsonNode>> jsonNodeQueryModelQuerySortFunction);

    @Override
    JsonNodeQuery withSortMulti(final Function<JsonNodeQueryModel, List<QuerySort<JsonNode>>> sortFunction);

    @Override
    JsonNodeQuery plusExpansionPaths(final Function<JsonNodeExpansionModel<JsonNode>, ExpansionPathContainer<JsonNode>> m);

    @Override
    JsonNodeQuery withExpansionPaths(final Function<JsonNodeExpansionModel<JsonNode>, ExpansionPathContainer<JsonNode>> m);

    @Override
    JsonNodeQuery withFetchTotal(final boolean fetchTotal);

    @Override
    JsonNodeQuery withLimit(final Long limit);

    @Override
    JsonNodeQuery withOffset(final Long offset);
}
