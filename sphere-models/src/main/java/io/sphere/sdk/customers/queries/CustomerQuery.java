package io.sphere.sdk.customers.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

/**
 * Fetches multiple customers.
 *
 * {@doc.gen summary customers}
 *
 * <p>Example for fetching a customer by id and expand the customer group:</p>
 * {@include.example io.sphere.sdk.customers.queries.CustomerQueryTest#customerGroupReferenceExpansion()}
 */
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
        return withPredicates(m -> m.email().is(email));
    }

}
