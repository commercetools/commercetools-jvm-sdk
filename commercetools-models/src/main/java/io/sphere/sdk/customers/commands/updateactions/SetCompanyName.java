package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Sets a new company name for the customer.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#setCompanyName()}
 *
 * @see Customer
 */
public final class SetCompanyName extends UpdateActionImpl<Customer> {
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
