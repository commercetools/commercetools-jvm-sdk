package io.sphere.sdk.productselections.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.productselections.ProductSelectionAssignment;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;


import java.util.List;
import java.util.function.Function;

public interface ProductSelectionAssignmentInStoreQuery extends MetaModelQueryDsl<ProductSelectionAssignment, ProductSelectionAssignmentInStoreQuery, ProductSelectionAssignmentQueryModel> {

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
    static TypeReference<PagedQueryResult<ProductSelectionAssignment>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ProductSelectionAssignment>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductSelectionAssignment>>";
            }
        };
    }

    static ProductSelectionAssignmentInStoreQuery of(final String storeKey) {
        return new ProductSelectionAssignmentInStoreQueryImpl(storeKey);
    }

    @Override
    ProductSelectionAssignmentInStoreQuery plusPredicates(final Function<ProductSelectionAssignmentQueryModel, QueryPredicate<ProductSelectionAssignment>> m);

    @Override
    ProductSelectionAssignmentInStoreQuery plusPredicates(final QueryPredicate<ProductSelectionAssignment> queryPredicate);

    @Override
    ProductSelectionAssignmentInStoreQuery plusPredicates(final List<QueryPredicate<ProductSelectionAssignment>> queryPredicates);

    @Override
    ProductSelectionAssignmentInStoreQuery plusSort(final Function<ProductSelectionAssignmentQueryModel, QuerySort<ProductSelectionAssignment>> m);

    @Override
    ProductSelectionAssignmentInStoreQuery plusSort(final List<QuerySort<ProductSelectionAssignment>> sort);

    @Override
    ProductSelectionAssignmentInStoreQuery plusSort(final QuerySort<ProductSelectionAssignment> sort);

    @Override
    ProductSelectionAssignmentInStoreQuery withPredicates(final Function<ProductSelectionAssignmentQueryModel, QueryPredicate<ProductSelectionAssignment>> predicateFunction);

    @Override
    ProductSelectionAssignmentInStoreQuery withPredicates(final QueryPredicate<ProductSelectionAssignment> queryPredicate);

    @Override
    ProductSelectionAssignmentInStoreQuery withPredicates(final List<QueryPredicate<ProductSelectionAssignment>> queryPredicates);

    @Override
    ProductSelectionAssignmentInStoreQuery withSort(final Function<ProductSelectionAssignmentQueryModel, QuerySort<ProductSelectionAssignment>> m);

    @Override
    ProductSelectionAssignmentInStoreQuery withSort(final List<QuerySort<ProductSelectionAssignment>> sort);

    @Override
    ProductSelectionAssignmentInStoreQuery withSort(final QuerySort<ProductSelectionAssignment> sort);

    @Override
    ProductSelectionAssignmentInStoreQuery withSortMulti(final Function<ProductSelectionAssignmentQueryModel, List<QuerySort<ProductSelectionAssignment>>> m);

    @Override
    ProductSelectionAssignmentInStoreQuery plusExpansionPaths(final Function<ProductSelectionAssignmentExpansionModel<ProductSelectionAssignment>, ExpansionPathContainer<ProductSelectionAssignment>> m);

    @Override
    ProductSelectionAssignmentInStoreQuery withExpansionPaths(final Function<ProductSelectionAssignmentExpansionModel<ProductSelectionAssignment>, ExpansionPathContainer<ProductSelectionAssignment>> m);

    @Override
    ProductSelectionAssignmentInStoreQuery withFetchTotal(final boolean fetchTotal);

    @Override
    ProductSelectionAssignmentInStoreQuery withLimit(final Long limit);

    @Override
    ProductSelectionAssignmentInStoreQuery withOffset(final Long offset);
    
}
