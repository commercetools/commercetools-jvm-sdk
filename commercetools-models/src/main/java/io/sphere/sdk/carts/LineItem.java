package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPriceForQuantity;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 <p>A line item is a snapshot of a product variant at the time it was added to the cart.</p>

 <p>A LineItem can have {@link io.sphere.sdk.types.Custom custom fields}.</p>

 @see io.sphere.sdk.carts.commands.updateactions.AddLineItem
 @see io.sphere.sdk.carts.commands.updateactions.RemoveLineItem
 @see io.sphere.sdk.carts.commands.updateactions.ChangeLineItemQuantity
 @see Order#getLineItems()
 @see Cart#getLineItems()
 */
@JsonDeserialize(as=LineItemImpl.class)
@ResourceValue(abstractResourceClass = true)
public interface LineItem extends LineItemLike {

    String getProductId();

    LocalizedString getName();

    ProductVariant getVariant();

    Price getPrice();

    Set<ItemState> getState();

    @Nullable
    TaxRate getTaxRate();

    @Nullable
    Reference<Channel> getSupplyChannel();

    @Nullable
    Reference<Channel> getDistributionChannel();

    @Nullable
    ZonedDateTime getAddedAt();

    @Nullable
    LocalizedString getProductSlug();

    @Override
    String getId();

    @Override
    Long getQuantity();

    @Override
    CustomFields getCustom();

    @Override
    MonetaryAmount getTotalPrice();

    @Override
    List<DiscountedLineItemPriceForQuantity> getDiscountedPricePerQuantity();

    @Nullable
    @Override
    TaxedItemPrice getTaxedPrice();

    LineItemPriceMode getPriceMode();

    @Nullable
    Reference<ProductType> getProductType();

    LineItemMode getLineItemMode();

    /**
     * Container for the sub-quantity of the line item quantity for the specific
     * address when multiple shipping addresses are required.
     * @return ItemShippingDetails
     */
    @Nullable
    ItemShippingDetails getShippingDetails();

    @Nullable
    ZonedDateTime getLastModifiedAt();

    @Nullable
    String getProductKey();

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "line-item";
    }
}
