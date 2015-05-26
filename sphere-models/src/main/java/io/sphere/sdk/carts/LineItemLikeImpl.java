package io.sphere.sdk.carts;

import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.models.Base;

import java.util.Optional;
import java.util.Set;

abstract class LineItemLikeImpl extends Base implements LineItemLike {
    private final String id;
    private final Set<ItemState> state;
    private final long quantity;
    private final DiscountedLineItemPrice discountedPrice;

    public LineItemLikeImpl(final String id, final Set<ItemState> state, final long quantity, final Optional<DiscountedLineItemPrice> discountedPrice) {
        this.discountedPrice = discountedPrice.orElse(null);
        this.id = id;
        this.state = state;
        this.quantity = quantity;
    }

    public Optional<DiscountedLineItemPrice> getDiscountedPrice() {
        return Optional.ofNullable(discountedPrice);
    }

    public String getId() {
        return id;
    }

    public long getQuantity() {
        return quantity;
    }

    public Set<ItemState> getState() {
        return state;
    }
}
