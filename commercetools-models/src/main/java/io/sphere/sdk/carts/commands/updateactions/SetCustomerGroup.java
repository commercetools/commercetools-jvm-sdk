package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.models.ResourceIdentifier;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Sets the customer group.
 * <p>
 * {@doc.gen intro}
 * <p>
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setCustomerGroup()}
 */
public final class SetCustomerGroup extends UpdateActionImpl<Cart> {
    @Nullable
    private final ResourceIdentifier<CustomerGroup> customerGroup;

    private SetCustomerGroup(final ResourceIdentifier<CustomerGroup> customerGroup) {
        super("setCustomerGroup");
        this.customerGroup = customerGroup;
    }

    public static SetCustomerGroup of(@Nullable final ResourceIdentifiable<CustomerGroup> customerGroup) {
        final ResourceIdentifier<CustomerGroup> resourceIdentifier = Optional.ofNullable(customerGroup)
                .map(ResourceIdentifiable::toResourceIdentifier)
                .orElse(null);
        return new SetCustomerGroup(resourceIdentifier);
    }

    public ResourceIdentifier<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }
}
