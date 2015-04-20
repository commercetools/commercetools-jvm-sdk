package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;

import java.util.Optional;

/**
 * Sets a new company name for the customer
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setCompanyName()}
 */
public class SetCompanyName extends UpdateAction<Customer> {
    private final Optional<String> companyName;

    private SetCompanyName(final Optional<String> companyName) {
        super("setCompanyName");
        this.companyName = companyName;
    }

    public static SetCompanyName of(final Optional<String> companyName) {
        return new SetCompanyName(companyName);
    }

    public static SetCompanyName of(final String companyName) {
        return of(Optional.of(companyName));
    }

    public Optional<String> getCompanyName() {
        return companyName;
    }
}
