package io.sphere.sdk.customergroups.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;

/**
 {@doc.gen summary customer groups}
 */
public class CustomerGroupQuery extends DefaultModelQuery<CustomerGroup> {
    public CustomerGroupQuery() {
        super(CustomerGroupEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<CustomerGroup>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<CustomerGroup>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<CustomerGroup>>";
            }
        };
    }

    public QueryDsl<CustomerGroup> byName(final String name) {
        return withPredicate(model().name().is(name));
    }

    public static CustomerGroupQueryModel model() {
        return CustomerGroupQueryModel.get();
    }
}
