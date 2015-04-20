package io.sphere.sdk.customers.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;

public class CustomerQuery extends DefaultModelQuery<Customer> {
    private CustomerQuery() {
        super(CustomersEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<Customer>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Customer>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Customer>>";
            }
        };
    }

    public static CustomerQueryModel model() {
        return CustomerQueryModel.get();
    }

    public static CustomerQuery of() {
        return new CustomerQuery();
    }
}
