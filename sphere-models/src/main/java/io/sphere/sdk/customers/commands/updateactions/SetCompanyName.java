package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Sets a new company name for the customer
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setCompanyName()}
 */
public class SetCompanyName extends UpdateAction<Customer> {
    @Nullable
    private final String companyName;

    private SetCompanyName(@Nullable final String companyName) {
        super("setCompanyName");
        this.companyName = companyName;
    }

    public static SetCompanyName of(@Nullable final String companyName) {
        return new SetCompanyName(companyName);
    }

    @Nullable
    public String getCompanyName() {
        return companyName;
    }
}
