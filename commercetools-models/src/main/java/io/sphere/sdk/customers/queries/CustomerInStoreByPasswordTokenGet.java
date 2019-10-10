package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

public interface CustomerInStoreByPasswordTokenGet extends MetaModelGetDsl<Customer, Customer, CustomerInStoreByPasswordTokenGet, CustomerExpansionModel<Customer>> {

    static CustomerInStoreByPasswordTokenGet of(final String storeKey, final String token) {
        return new CustomerInStoreByPasswordTokenGetImpl(storeKey, token);
    }

    static CustomerInStoreByPasswordTokenGet of(final String storeKey, final CustomerToken token) {
        return of(storeKey, token.getValue());
    }

    @Override
    List<ExpansionPath<Customer>> expansionPaths();

    @Override
    CustomerInStoreByPasswordTokenGet plusExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerInStoreByPasswordTokenGet withExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerInStoreByPasswordTokenGet withExpansionPaths(final List<ExpansionPath<Customer>> expansionPaths);
}