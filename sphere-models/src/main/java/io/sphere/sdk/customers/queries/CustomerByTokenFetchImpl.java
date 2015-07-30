package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

import java.util.Collections;

final class CustomerByTokenFetchImpl extends MetaModelFetchDslImpl<Customer, Customer, CustomerByTokenFetch, CustomerExpansionModel<Customer>> implements CustomerByTokenFetch {

    CustomerByTokenFetchImpl(final String token) {
        super(CustomerEndpoint.ENDPOINT, "", CustomerExpansionModel.of(), CustomerByTokenFetchImpl::new, Collections.singletonList(HttpQueryParameter.of("token", token)));
    }

    public CustomerByTokenFetchImpl(MetaModelFetchDslBuilder<Customer, Customer, CustomerByTokenFetch, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}

