package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

/**
 * DEPRECATED class.
 *
 */
public interface CustomerByTokenGet extends MetaModelGetDsl<Customer, Customer, CustomerByTokenGet, CustomerExpansionModel<Customer>> {
    /**
     * @deprecated use {@link CustomerByPasswordTokenGet#of(String)}
     * @param token token value belonging to the customer
     * @return CustomerByTokenGet
     */
    @Deprecated
    static CustomerByTokenGet of(final String token) {
        return new CustomerByTokenGetImpl(token);
    }

    /**
     * @deprecated use {@link CustomerByPasswordTokenGet#of(CustomerToken)}
     * @param token the token belonging to the customer
     * @return CustomerByTokenGet
     */
    @Deprecated
    static CustomerByTokenGet of(final CustomerToken token) {
        return new CustomerByTokenGetImpl(token.getValue());
    }

    @Override
    List<ExpansionPath<Customer>> expansionPaths();

    @Override
    CustomerByTokenGet plusExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByTokenGet withExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByTokenGet withExpansionPaths(final List<ExpansionPath<Customer>> expansionPaths);
}
