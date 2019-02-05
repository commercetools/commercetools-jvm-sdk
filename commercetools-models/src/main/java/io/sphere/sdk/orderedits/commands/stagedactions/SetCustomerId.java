package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.Optional;

public final class SetCustomerId extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final String customerId;

    @JsonCreator
    private SetCustomerId(@Nullable final String customerId) {
        super("setCustomerId");
        this.customerId = customerId;
    }

    public static SetCustomerId of(@Nullable final String customerId) {
        return new SetCustomerId(customerId);
    }

    public static SetCustomerId ofCustomer(@Nullable final Referenceable<Customer> customer) {
        return of(Optional.ofNullable(customer).map(c -> c.toReference().getId()).orElse(null));
    }

    @Nullable
    public String getCustomerId() {
        return customerId;
    }
}