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
