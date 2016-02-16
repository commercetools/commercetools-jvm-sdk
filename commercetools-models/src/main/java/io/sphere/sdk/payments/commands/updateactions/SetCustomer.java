package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Sets the reference to the customer. If not defined, the reference is unset.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#setCustomer()}
 *
 * @see Payment
 */
public final class SetCustomer extends UpdateActionImpl<Payment> {
    @Nullable
    private final Reference<Customer> customer;

    private SetCustomer(@Nullable final Reference<Customer> customer) {
        super("setCustomer");
        this.customer = customer;
    }

    public static SetCustomer of(@Nullable final Referenceable<Customer> customer) {
        final Reference<Customer> customerReference = Optional.ofNullable(customer).map(x -> x.toReference()).orElse(null);
        return new SetCustomer(customerReference);
    }

    @Nullable
    public Reference<Customer> getCustomer() {
        return customer;
    }
}
