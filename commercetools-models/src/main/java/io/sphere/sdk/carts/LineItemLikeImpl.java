package io.sphere.sdk.carts;

import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Set;

abstract class LineItemLikeImpl extends Base implements LineItemLike {
    private final String id;
    private final Set<ItemState> state;
    private final Long quantity;
    @Nullable
    private final DiscountedLineItemPrice discountedPrice;

    public LineItemLikeImpl(final String id, final Set<ItemState> state, final Long quantity, @Nullable final DiscountedLineItemPrice discountedPrice) {
        this.discountedPrice = discountedPrice;
        this.id = id;
        this.state = state;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Set<ItemState> getState() {
        return state;
    }
}
