package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.taxcategories.TaxRate;

import java.util.Optional;
import java.util.Set;

/**
 *
 * @see LineItemImportDraftBuilder
 */
public interface LineItemImportDraft {
    LocalizedStrings getName();

    Price getPrice();

    Optional<String> getProductId();

    long getQuantity();

    Optional<Set<ItemState>> getState();

    Optional<Reference<Channel>> getSupplyChannel();

    Optional<TaxRate> getTaxRate();

    ProductVariantImportDraft getVariant();
}
