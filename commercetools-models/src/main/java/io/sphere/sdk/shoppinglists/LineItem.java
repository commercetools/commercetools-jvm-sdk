package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.shoppinglists.commands.updateactions.*;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * A LineItem is a reference to a product variant in a product in its current version with a quantity.
 * A LineItem can have {@link io.sphere.sdk.types.Custom custom fields}.
 *
 * @see AddLineItem
 * @see RemoveLineItem
 * @see ChangeLineItemQuantity
 * @see SetLineItemCustomField
 * @see SetLineItemCustomType
 *
 * @see ShoppingList#getLineItems()
 */
@JsonDeserialize(as = LineItemImpl.class)
@ResourceValue(abstractResourceClass = true)
public interface LineItem {
    String getId();

    String getProductId();

    @Nullable
    Integer getVariantId();

    Reference<ProductType> getProductType();

    Long getQuantity();

    @Nullable
    CustomFields getCustom();

    LocalizedString getName();

    ZonedDateTime getAddedAt();

    @Nullable
    ZonedDateTime getDeactivatedAt();

    @Nullable
    LocalizedString getProductSlug();

    @Nullable
    ProductVariant getVariant();

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
