package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Sets/unsets the customer belonging to a review.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandIntegrationTest#setCustomer()}
 */
public final class SetCustomer extends UpdateActionImpl<Review> {
    @Nullable
    private final ResourceIdentifier<Customer> customer;

    private SetCustomer(@Nullable final ResourceIdentifier<Customer> customer) {
        super("setCustomer");
        this.customer = customer;
    }

    public static SetCustomer of(final ResourceIdentifiable<Customer> customer) {
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
