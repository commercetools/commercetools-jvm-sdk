package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

import static java.util.Arrays.asList;

public class CustomerByTokenFetchImpl extends MetaModelFetchDslImpl<Customer, CustomerByTokenFetch, CustomerExpansionModel<Customer>> implements CustomerByTokenFetch {

    CustomerByTokenFetchImpl(final String token) {
        super(CustomerEndpoint.ENDPOINT, "", CustomerExpansionModel.of(), CustomerByTokenFetchImpl::new, asList(HttpQueryParameter.of("token", token)));
    }

    public CustomerByTokenFetchImpl(MetaModelFetchDslBuilder<Customer, CustomerByTokenFetch, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}

