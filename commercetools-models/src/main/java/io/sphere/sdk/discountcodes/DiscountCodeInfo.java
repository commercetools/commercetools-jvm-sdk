package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

public final class DiscountCodeInfo extends Base {
    private final Reference<DiscountCode> discountCode;
    private final DiscountCodeState state;

    @JsonCreator
    private DiscountCodeInfo(final Reference<DiscountCode> discountCode, final DiscountCodeState state) {
        this.discountCode = discountCode;
        this.state = state;
    }

    public Reference<DiscountCode> getDiscountCode() {
        return discountCode;
    }

    public DiscountCodeState getState() {
        return state;
    }

    public static DiscountCodeInfo of(final Referenceable<DiscountCode> discountCode, final DiscountCodeState state) {
        return new DiscountCodeInfo(discountCode.toReference(), state);
    }
}
