package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Sets the reference to the customer. If not defined, the reference is unset.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#setCustomer()}
 *
 * @see ShoppingList#getCustomer()
 */
public final class SetCustomer extends UpdateActionImpl<ShoppingList> {
    @Nullable
    private final ResourceIdentifier<Customer> customer;

    private SetCustomer(@Nullable final ResourceIdentifier<Customer> customer) {
        super("setCustomer");
        this.customer = customer;
    }

    public static SetCustomer of(@Nullable final ResourceIdentifiable<Customer> customer) {
        final ResourceIdentifier<Customer> resourceIdentifier = Optional.ofNullable(customer)
                .map(ResourceIdentifiable::toResourceIdentifier)
                .orElse(null);
        return new SetCustomer(resourceIdentifier);
    }

    public static SetCustomer ofUnset() {
        return new SetCustomer(null);
    }

    @Nullable
    public ResourceIdentifier<Customer> getCustomer() {
        return customer;
    }
}
