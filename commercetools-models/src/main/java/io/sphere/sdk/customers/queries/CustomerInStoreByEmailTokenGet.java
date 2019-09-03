package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

public interface CustomerInStoreByEmailTokenGet extends MetaModelGetDsl<Customer, Customer, CustomerInStoreByEmailTokenGet, CustomerExpansionModel<Customer>> {

    static CustomerInStoreByEmailTokenGet of(final String storeKey, final String token) {
        return new CustomerInStoreByEmailTokenGetImpl(storeKey, token);
    }

    static CustomerInStoreByEmailTokenGet of(final String storeKey, final CustomerToken token) {
        return of(storeKey, token.getValue());
    }

    @Override
    List<ExpansionPath<Customer>> expansionPaths();

    @Override
    CustomerInStoreByEmailTokenGet plusExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerInStoreByEmailTokenGet withExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerInStoreByEmailTokenGet withExpansionPaths(final List<ExpansionPath<Customer>> expansionPaths);
}