package io.sphere.sdk.customers.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

public interface CustomerQuery extends MetaModelQueryDsl<Customer, CustomerQuery, CustomerQueryModel, CustomerExpansionModel<Customer>> {
   static TypeReference<PagedQueryResult<Customer>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Customer>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Customer>>";
            }
        };
    }

    static CustomerQuery of() {
        return new CustomerQueryImpl();
    }

    default CustomerQuery byEmail(final String email) {
        return withPredicate(m -> m.email().is(email));
    }

}
