package io.sphere.sdk.discountcodes;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

public class DiscountCodeReference extends Base {
    private final Reference<DiscountCode> discountCode;
    private final DiscountCodeState state;

    private DiscountCodeReference(final Reference<DiscountCode> discountCode, final DiscountCodeState state) {
        this.discountCode = discountCode;
        this.state = state;
    }

    public Reference<DiscountCode> getDiscountCode() {
        return discountCode;
    }

    public DiscountCodeState getState() {
        return state;
    }

    public static DiscountCodeReference of(final Referenceable<DiscountCode> discountCode, final DiscountCodeState state) {
        return new DiscountCodeReference(discountCode.toReference(), state);
    }
}
