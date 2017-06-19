package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum LineItemPriceMode implements SphereEnumeration {
    /**
     * The price is selected from the product variant. This is the default mode.
     */
    PLATFORM,

    /**
     * The line item price with the total was set externally. Cart discounts are disabled for the line items with this price mode.
     * Although a line item with this price mode has both {@code price} and {@code totalPrice} externally set, only {@code totalPrice}
     * will be used to calculate the total price of a cart. All update actions that change the quantity of a line item
     * with this price mode can set the new price with the {@code externalTotal} field. If the {@code externalTotal} field is not given
     * in an update action that changes line item quantity then the external price is unset and the price mode is set back to {@code PLATFORM}.
     *
     * @see io.sphere.sdk.carts.commands.updateactions.SetLineItemTotalPrice
     */
    EXTERNAL_TOTAL,

    /**
     * The line item price was set externally. Cart discounts can apply to line items with this price mode. All update
     * actions that change the quantity of a line item with this price mode require the {@code EXTERNAL_PRICE} field to be given.
     */
    EXTERNAL_PRICE;

    @JsonCreator
    public static LineItemPriceMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}