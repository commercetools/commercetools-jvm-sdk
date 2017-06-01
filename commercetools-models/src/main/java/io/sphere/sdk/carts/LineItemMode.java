package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.commands.updateactions.RemoveLineItem;
import io.sphere.sdk.carts.commands.updateactions.SetLineItemTotalPrice;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * Enumerates the available modes for a line item.
 */
public enum LineItemMode implements SphereEnumeration {
    /**
     * The line item was added during cart creation {@link io.sphere.sdk.carts.commands.CartCreateCommand}
     * or with the update action {@link io.sphere.sdk.carts.commands.updateactions.AddLineItem}.
     *
     * Its quantity can be changed without restrictions.
     */
    STANDARD,

    /**
     * The line item was added automatically, because a {@link io.sphere.sdk.cartdiscounts.GiftLineItemCartDiscountValue} has added a free gift to the cart.
     *
     * The quantity can not be increased and it won't be merged when the same product variant is added.
     * If the gift is removed {@link RemoveLineItem}, an entry is added to the {@link Cart#getRefusedGifts()}
     * and the discount won't be applied again to the cart. The price can not be externally changed via {@link SetLineItemTotalPrice}.
     */
    GIFT_LINE_ITEM;

    @JsonCreator
    public static LineItemMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
