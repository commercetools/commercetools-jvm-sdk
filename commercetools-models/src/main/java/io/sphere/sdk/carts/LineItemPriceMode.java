package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum LineItemPriceMode implements SphereEnumeration {
    /**
     * The price is selected from the product variant. This is the default mode.
     */
    PLATFORM,

    /**
     * The price was set externally. Cart discounts are disabled for the line items with this price mode. Any changes to the line item quantity will reset it back to the {@code PLATFORM} price mode.
     * @see io.sphere.sdk.carts.commands.updateactions.SetLineItemTotalPrice
     */
    EXTERNAL_TOTAL;

    @JsonCreator
    public static LineItemPriceMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}