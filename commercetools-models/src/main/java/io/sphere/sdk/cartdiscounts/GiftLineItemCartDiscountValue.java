package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.carts.LineItemMode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Adds a free line item as a gift to the cart. The line item will have the {@link LineItemMode#GIFT_LINE_ITEM}.
 * Like all other line items, it has the {@link LineItem#getPrice()} field set (it is therefore necessary that the
 * variant has a price defined that can be selected for each cart the discount should be applied to).
 *
 * The {@link LineItem#getTotalPrice()} has a `centAmount` of `0`.
 * The {@link LineItem#getDiscountedPricePerQuantity()} discounts the full price and links back to this cart discount.
 * If at creation time the discount can not be applied to any cart (e.g. because the product, the variant, or a channel does not exist), the creation fails.
 *
 *  The discount will not be applied to a cart if it either has become invalid since the creation (e.g. because the product, the variant, or a channel have been deleted)
 *  or because no price can be selected for the particular cart.
 */
public final class GiftLineItemCartDiscountValue extends Base implements CartDiscountValue {
    private ResourceIdentifier<Product> product;

    private Integer variantId;

    @Nullable
    private ResourceIdentifier<Channel> supplyChannel;

    @Nullable
    private ResourceIdentifier<Channel> distributionChannel;

    @JsonCreator
    private GiftLineItemCartDiscountValue(final ResourceIdentifier<Product> product,
                                         final Integer variantId,
                                         @Nullable final ResourceIdentifier<Channel> supplyChannel,
                                         @Nullable final ResourceIdentifier<Channel> distributionChannel) {
        this.product = product;
        this.variantId = variantId;
        this.supplyChannel = supplyChannel;
        this.distributionChannel = distributionChannel;
    }

    public ResourceIdentifier<Product> getProduct() {
        return product;
    }

    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public ResourceIdentifier<Channel> getSupplyChannel() {
        return supplyChannel;
    }

    @Nullable
    public ResourceIdentifier<Channel> getDistributionChannel() {
        return distributionChannel;
    }

    public static GiftLineItemCartDiscountValue of(final ResourceIdentifier<Product> product,
                                                   final Integer variantId,
                                                   @Nullable final ResourceIdentifier<Channel> supplyChannel,
                                                   @Nullable final ResourceIdentifier<Channel> distributionChannel) {
        return new GiftLineItemCartDiscountValue(product, variantId, supplyChannel, distributionChannel);
    }
}
