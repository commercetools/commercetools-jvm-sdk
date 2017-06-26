package io.sphere.sdk.customers.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.http.UriTemplate;
import io.sphere.sdk.http.UrlBuilder;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class CustomerByPasswordTokenGetImpl extends MetaModelGetDslImpl<Customer, Customer, CustomerByPasswordTokenGet, CustomerExpansionModel<Customer>> implements CustomerByPasswordTokenGet {
    static final UriTemplate GET_BY_PASSWORD_TOKEN = UriTemplate.of("/customers/password-token={token}");

    CustomerByPasswordTokenGetImpl(final String token) {
        super("", buildEndpoint(token), CustomerExpansionModel.of(), CustomerByPasswordTokenGetImpl::new);
    }

    private static JsonEndpoint<Customer> buildEndpoint(final String token) {
        final String endpoint = UrlBuilder.of(GET_BY_PASSWORD_TOKEN)
                .addUriParameter(token)
                .build();
        return JsonEndpoint.of(Customer.typeReference(), endpoint);
    }

    public CustomerByPasswordTokenGetImpl(final MetaModelGetDslBuilder<Customer, Customer, CustomerByPasswordTokenGet, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}

