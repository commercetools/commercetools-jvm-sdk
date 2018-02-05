package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPriceForQuantity;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Set;

/**
 <p>A custom line item is a generic item that can be added to the cart but is not bound to a product. You can use it for discounts (negative money), vouchers, complex cart rules, additional services or fees. You control the lifecycle of this item.</p>

 <p>A CustomLineItem can have {@link io.sphere.sdk.types.Custom custom fields}.</p>


 <p>Example for adding a {@link CustomLineItem} to a {@link Cart}:</p>
 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#addCustomLineItem()}

 @see CustomLineItemDraftImpl
 @see io.sphere.sdk.carts.commands.updateactions.AddCustomLineItem
 @see io.sphere.sdk.carts.commands.updateactions.RemoveCustomLineItem
 @see Order#getCustomLineItems()
 @see Cart#getCustomLineItems()
 */
@JsonDeserialize(as=CustomLineItemImpl.class)
public interface CustomLineItem extends LineItemLike {
    LocalizedString getName();

    /**
     * The positive or negative monetary amount of this custom line item.
     * @return monetary amount
     * @see io.sphere.sdk.carts.commands.updateactions.ChangeCustomLineItemMoney
     */
    MonetaryAmount getMoney();

    String getSlug();

    Set<ItemState> getState();

    @Nullable
    Reference<TaxCategory> getTaxCategory();

    @Nullable
    TaxRate getTaxRate();

    @Override
    String getId();

    /**
     * The item count of this custom line item.
     * @return quantity
     * @see io.sphere.sdk.carts.commands.updateactions.ChangeCustomLineItemQuantity
     */
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


    /**
     *  Container for the sub-quantity of the line item quantity for the specific address when multiple shipping addresses are required
     * @return ItemShippingDetails
     */
    @Nullable
    ItemShippingDetails getShippingDetails();

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "custom-line-item";
    }
}
