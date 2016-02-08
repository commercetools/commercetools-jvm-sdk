package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Sets the customer group for a customer.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setCustomerGroup()}
 *
 * @see Customer
 */
public final class SetCustomerGroup extends UpdateActionImpl<Customer> {
    @Nullable
    private final Reference<CustomerGroup> customerGroup;

    private SetCustomerGroup(final Referenceable<CustomerGroup> customerGroup) {
        super("setCustomerGroup");
        this.customerGroup = Optional.ofNullable(customerGroup)
                .map(x -> x.toReference().filled(null))
                .orElse(null);
    }

    public static SetCustomerGroup of(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        return new SetCustomerGroup(customerGroup);
    }

    @Nullable
    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }
}
