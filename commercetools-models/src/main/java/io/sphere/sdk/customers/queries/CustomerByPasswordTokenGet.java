package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerPasswordResetCommand;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

/**
 * Fetches a cusomter by a known password reset token.
 *
 * {@include.example io.sphere.sdk.customers.queries.CustomerByPasswordTokenGetIntegrationTest#execution()}
 *
 * @see Customer
 * @see io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand
 * @see CustomerPasswordResetCommand
 */
public interface CustomerByPasswordTokenGet extends MetaModelGetDsl<Customer, Customer, CustomerByPasswordTokenGet, CustomerExpansionModel<Customer>> {
    static CustomerByPasswordTokenGet of(final String token) {
        return new CustomerByPasswordTokenGetImpl(token);
    }

    static CustomerByPasswordTokenGet of(final CustomerToken token) {
        return of(token.getValue());
    }

    @Override
    List<ExpansionPath<Customer>> expansionPaths();

    @Override
    CustomerByPasswordTokenGet plusExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByPasswordTokenGet withExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByPasswordTokenGet withExpansionPaths(final List<ExpansionPath<Customer>> expansionPaths);
}
