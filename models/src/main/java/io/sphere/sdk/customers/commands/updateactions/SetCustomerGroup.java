package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.util.Optional;

/**
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setCustomerGroup()}
 */
public class SetCustomerGroup extends UpdateAction<Customer> {
    private final Optional<Reference<CustomerGroup>> customerGroup;

    private SetCustomerGroup(final Optional<Reference<CustomerGroup>> customerGroup) {
        super("setCustomerGroup");
        this.customerGroup = customerGroup.map(e -> e.filled(Optional.empty()));
    }

    public static SetCustomerGroup of(final Optional<Reference<CustomerGroup>> customerGroup) {
        return new SetCustomerGroup(customerGroup);
    }

    public static SetCustomerGroup of(final Referenceable<CustomerGroup> customerGroup) {
        return of(Optional.of(customerGroup.toReference()));
    }

    public Optional<Reference<CustomerGroup>> getCustomerGroup() {
        return customerGroup;
    }
}
