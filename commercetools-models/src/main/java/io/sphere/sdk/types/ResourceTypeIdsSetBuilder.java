package io.sphere.sdk.types;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.models.Asset;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.commands.updateactions.AddInterfaceInteraction;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.reviews.Review;

import java.util.HashSet;
import java.util.Set;

public final class ResourceTypeIdsSetBuilder extends Base implements Builder<Set<String>> {
    private final Set<String> resourceTypeIds = new HashSet<>();

    private ResourceTypeIdsSetBuilder() {
    }

    public static ResourceTypeIdsSetBuilder of() {
        return new ResourceTypeIdsSetBuilder();
    }

    public ResourceTypeIdsSetBuilder add(final String resourceTypeId) {
        resourceTypeIds.add(resourceTypeId);
        return this;
    }

    public ResourceTypeIdsSetBuilder addCategories() {
        return add(Category.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addCustomers() {
        return add(Customer.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addCartsAndOrders() {
        return add(Cart.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addLineItems() {
        return add(LineItem.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addCustomLineItems() {
        return add(CustomLineItem.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addPrices() {
        return add(Price.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addPayments() {
        return add(Payment.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addPaymentInterfaceInteractions() {
        return add(AddInterfaceInteraction.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addReviews() {
        return add(Review.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addChannels() {
        return add(Channel.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addInventoryEntries() {
        return add(InventoryEntry.resourceTypeId());
    }

    public ResourceTypeIdsSetBuilder addAssets() {
        return add(Asset.resourceTypeId());
    }

    @Override
    public Set<String> build() {
        return new HashSet<>(resourceTypeIds);
    }
}
