package io.sphere.sdk.customers.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;

import java.util.List;
import java.util.function.Function;

public interface CustomerInStoreQuery extends MetaModelQueryDsl<Customer, CustomerInStoreQuery, CustomerQueryModel, CustomerExpansionModel<Customer>> {

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
    static TypeReference<PagedQueryResult<Customer>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Customer>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Customer>>";
            }
        };
    }

    static CustomerInStoreQuery of(final String storeKey) {
        return new CustomerInStoreQueryImpl(storeKey);
    }

    @Override
    CustomerInStoreQuery plusPredicates(final Function<CustomerQueryModel, QueryPredicate<Customer>> m);

    @Override
    CustomerInStoreQuery plusPredicates(final QueryPredicate<Customer> queryPredicate);

    @Override
    CustomerInStoreQuery plusPredicates(final List<QueryPredicate<Customer>> queryPredicates);

    @Override
    CustomerInStoreQuery plusSort(final Function<CustomerQueryModel, QuerySort<Customer>> m);

    @Override
    CustomerInStoreQuery plusSort(final List<QuerySort<Customer>> sort);

    @Override
    CustomerInStoreQuery plusSort(final QuerySort<Customer> sort);

    @Override
    CustomerInStoreQuery withPredicates(final Function<CustomerQueryModel, QueryPredicate<Customer>> predicateFunction);

    @Override
    CustomerInStoreQuery withPredicates(final QueryPredicate<Customer> queryPredicate);

    @Override
    CustomerInStoreQuery withPredicates(final List<QueryPredicate<Customer>> queryPredicates);

    @Override
    CustomerInStoreQuery withSort(final Function<CustomerQueryModel, QuerySort<Customer>> m);

    @Override
    CustomerInStoreQuery withSort(final List<QuerySort<Customer>> sort);

    @Override
    CustomerInStoreQuery withSort(final QuerySort<Customer> sort);

    @Override
    CustomerInStoreQuery withSortMulti(final Function<CustomerQueryModel, List<QuerySort<Customer>>> m);

    @Override
    CustomerInStoreQuery plusExpansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPathContainer<Customer>> m);

    @Override
    CustomerInStoreQuery withExpansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPathContainer<Customer>> m);

    @Override
    CustomerInStoreQuery withFetchTotal(final boolean fetchTotal);

    @Override
    CustomerInStoreQuery withLimit(final Long limit);

    @Override
    CustomerInStoreQuery withOffset(final Long offset);

    default CustomerInStoreQuery byEmail(final String email) {
        return withPredicates(m -> m.lowercaseEmail().is(email.toLowerCase()));
    }
    
}
