package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerPasswordResetCommand;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

/**
 * Fetches a customer by a known email token.
 *
 * {@include.example io.sphere.sdk.customers.queries.CustomerByEmailTokenGetIntegrationTest#execution()}
 *
 * @see Customer
 * @see io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommand
 * @see CustomerPasswordResetCommand
 */
public interface CustomerByEmailTokenGet extends MetaModelGetDsl<Customer, Customer, CustomerByEmailTokenGet, CustomerExpansionModel<Customer>> {
    static CustomerByEmailTokenGet of(final String token) {
        return new CustomerByEmailTokenGetImpl(token);
    }

    static CustomerByEmailTokenGet of(final CustomerToken token) {
        return of(token.getValue());
    }

    @Override
    List<ExpansionPath<Customer>> expansionPaths();

    @Override
    CustomerByEmailTokenGet plusExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByEmailTokenGet withExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByEmailTokenGet withExpansionPaths(final List<ExpansionPath<Customer>> expansionPaths);
}
