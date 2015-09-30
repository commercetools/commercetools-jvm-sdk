package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.Set;

/**
 A custom line item is a generic item that can be added to the cart but is not bound to a product. You can use it for discounts (negative money), vouchers, complex cart rules, additional services or fees. You control the lifecycle of this item.

 <p>A CustomLineItem can have {@link io.sphere.sdk.types.Custom custom fields}.</p>
 */
@JsonDeserialize(as=CustomLineItemImpl.class)
public interface CustomLineItem extends LineItemLike {
    LocalizedString getName();

    MonetaryAmount getMoney();

    String getSlug();

    Set<ItemState> getState();

    Reference<TaxCategory> getTaxCategory();

    @Nullable
    TaxRate getTaxRate();

    @Override
    String getId();

    @Override
    Long getQuantity();

    @Override
    CustomFields getCustom();

    static String resourceTypeId() {
        return "custom-line-item";
    }
}
