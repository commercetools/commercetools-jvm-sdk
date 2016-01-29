package io.sphere.sdk.customergroups.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

/**
  Fetches multiple customer groups.

 {@doc.gen summary customer groups}

 {@include.example io.sphere.sdk.customergroups.queries.CustomerGroupQueryTest#byNames()}

 @see CustomerGroup
 */
public interface CustomerGroupQuery extends MetaModelQueryDsl<CustomerGroup, CustomerGroupQuery, CustomerGroupQueryModel, CustomerGroupExpansionModel<CustomerGroup>> {
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
    static TypeReference<PagedQueryResult<CustomerGroup>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<CustomerGroup>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<CustomerGroup>>";
            }
        };
    }

    default CustomerGroupQuery byName(final String name) {
        return withPredicates(m -> m.name().is(name));
    }

    static CustomerGroupQuery of() {
        return new CustomerGroupQueryImpl();
    }
}
