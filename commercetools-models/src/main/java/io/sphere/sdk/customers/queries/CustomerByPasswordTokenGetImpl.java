package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import java.util.Collections;

final class CustomerByPasswordTokenGetImpl extends MetaModelGetDslImpl<Customer, Customer, CustomerByPasswordTokenGet, CustomerExpansionModel<Customer>> implements CustomerByPasswordTokenGet {

    CustomerByPasswordTokenGetImpl(final String token) {
        super(CustomerEndpoint.ENDPOINT, "", CustomerExpansionModel.of(), CustomerByPasswordTokenGetImpl::new, Collections.singletonList(NameValuePair.of("token", token)));
    }

    public CustomerByPasswordTokenGetImpl(final MetaModelGetDslBuilder<Customer, Customer, CustomerByPasswordTokenGet, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}

