package io.sphere.sdk.customers.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.http.UriTemplate;
import io.sphere.sdk.http.UrlBuilder;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class CustomerByEmailTokenGetImpl extends MetaModelGetDslImpl<Customer, Customer, CustomerByEmailTokenGet, CustomerExpansionModel<Customer>> implements CustomerByEmailTokenGet {
    static final UriTemplate GET_BY_EMAIL_TOKEN = UriTemplate.of("/customers/email-token={token}");

    CustomerByEmailTokenGetImpl(final String token) {
        super("", buildEndpoint(token), CustomerExpansionModel.of(), CustomerByEmailTokenGetImpl::new);
    }

    private static JsonEndpoint<Customer> buildEndpoint(final String token) {
        final String endpoint = UrlBuilder.of(GET_BY_EMAIL_TOKEN)
                .addUriParameter(token)
                .build();
        return JsonEndpoint.of(Customer.typeReference(), endpoint);
    }

    public CustomerByEmailTokenGetImpl(final MetaModelGetDslBuilder<Customer, Customer, CustomerByEmailTokenGet, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }
}
