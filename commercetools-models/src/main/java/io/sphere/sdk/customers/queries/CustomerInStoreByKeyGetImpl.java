package io.sphere.sdk.customers.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class CustomerInStoreByKeyGetImpl extends MetaModelGetDslImpl<Customer, Customer, CustomerInStoreByKeyGet, CustomerExpansionModel<Customer>> implements CustomerInStoreByKeyGet {

    CustomerInStoreByKeyGetImpl(final String storeKey, final String key) {
        super("key=" + key, JsonEndpoint.of(Customer.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/customers"), CustomerExpansionModel.of(), CustomerInStoreByKeyGetImpl::new);
    }

    public CustomerInStoreByKeyGetImpl(final MetaModelGetDslBuilder<Customer, Customer, CustomerInStoreByKeyGet, CustomerExpansionModel<Customer>> builder) {
        super(builder);
    }

}
