package io.sphere.sdk.customers.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class CustomerInStoreByPasswordTokenGetImpl extends MetaModelGetDslImpl<Customer, Customer, CustomerInStoreByPasswordTokenGet, CustomerExpansionModel<Customer>> implements CustomerInStoreByPasswordTokenGet {

    CustomerInStoreByPasswordTokenGetImpl(final String storeKey, final String token) {
        super("", buildEndpoint(storeKey, token), CustomerExpansionModel.of(), CustomerInStoreByPasswordTokenGetImpl::new);
    }

    private static JsonEndpoint<Customer> buildEndpoint(final String storeKey, final String token) {
        final String endpoint = "/in-store/key=" + urlEncode(storeKey) + "/customers/password-token=" + token;
        return JsonEndpoint.of(Customer.typeReference(), endpoint);
    }

    public CustomerInStoreByPasswordTokenGetImpl(final MetaModelGetDslBuilder<Customer, Customer, CustomerInStoreByPasswordTokenGet, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}
