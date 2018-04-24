package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;

import javax.annotation.Nullable;

/**
 * Sets the customer group for a customer.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#setCustomerGroup()}
 *
 * @see Customer
 */
public final class SetCustomerGroup extends UpdateActionImpl<Customer> {
    @Nullable
    private final ResourceIdentifier<CustomerGroup> customerGroup;

    private SetCustomerGroup(final ResourceIdentifier<CustomerGroup> customerGroup) {
        super("setCustomerGroup");
        this.customerGroup = customerGroup;
    }

    public static SetCustomerGroup of(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        return new SetCustomerGroup(customerGroup.toReference());
    }

    public static SetCustomerGroup of(@Nullable final ResourceIdentifier<CustomerGroup> customerGroup) {
        return new SetCustomerGroup(customerGroup);
    }


    @Nullable
    public ResourceIdentifier<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }
}
