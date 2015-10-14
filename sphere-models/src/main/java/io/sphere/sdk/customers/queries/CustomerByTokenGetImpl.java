package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import java.util.Collections;

final class CustomerByTokenGetImpl extends MetaModelGetDslImpl<Customer, Customer, CustomerByTokenGet, CustomerExpansionModel<Customer>> implements CustomerByTokenGet {

    CustomerByTokenGetImpl(final String token) {
        super(CustomerEndpoint.ENDPOINT, "", CustomerExpansionModel.of(), CustomerByTokenGetImpl::new, Collections.singletonList(HttpQueryParameter.of("token", token)));
    }

    public CustomerByTokenGetImpl(final MetaModelGetDslBuilder<Customer, Customer, CustomerByTokenGet, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}

