package io.sphere.sdk.customers.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.http.UriTemplate;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class CustomerInStoreByEmailTokenGetImpl extends MetaModelGetDslImpl<Customer, Customer, CustomerInStoreByEmailTokenGet, CustomerExpansionModel<Customer>> implements CustomerInStoreByEmailTokenGet {

    static final UriTemplate GET_BY_EMAIL_TOKEN = UriTemplate.of("/customers/email-token={token}");

    CustomerInStoreByEmailTokenGetImpl(final String storeKey, final String token) {
        super("", buildEndpoint(storeKey, token), CustomerExpansionModel.of(), CustomerInStoreByEmailTokenGetImpl::new);
    }

    private static JsonEndpoint<Customer> buildEndpoint(final String storeKey, final String token) {
        final String endpoint = "/in-store/key=" + urlEncode(storeKey) + "/customers/email-token=" + token;
        return JsonEndpoint.of(Customer.typeReference(), endpoint);
    }

    public CustomerInStoreByEmailTokenGetImpl(final MetaModelGetDslBuilder<Customer, Customer, CustomerInStoreByEmailTokenGet, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }

}
