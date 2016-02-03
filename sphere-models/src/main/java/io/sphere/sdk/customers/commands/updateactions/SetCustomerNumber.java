package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

/**
 * Sets a string that uniquely identifies a customer. It can be used to create more human-readable (in contrast to ID) identifier for the customer.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setCustomerNumber()}
 *
 * @see Customer
 */
public final class SetCustomerNumber extends UpdateActionImpl<Customer> {
    private final String customerNumber;

    private SetCustomerNumber(final String customerNumber) {
        super("setCustomerNumber");
        this.customerNumber = customerNumber;
    }

    public static SetCustomerNumber of(final String customerNumber) {
        return new SetCustomerNumber(customerNumber);
    }

    public String getCustomerNumber() {
        return customerNumber;
    }
}
