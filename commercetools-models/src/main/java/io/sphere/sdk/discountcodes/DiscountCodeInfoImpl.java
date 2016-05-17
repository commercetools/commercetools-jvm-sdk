package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

final class DiscountCodeInfoImpl extends Base implements DiscountCodeInfo {
    private final Reference<DiscountCode> discountCode;
    private final DiscountCodeState state;

    @JsonCreator
    DiscountCodeInfoImpl(final Reference<DiscountCode> discountCode, final DiscountCodeState state) {
        this.discountCode = discountCode;
        this.state = state;
    }

    public Reference<DiscountCode> getDiscountCode() {
        return discountCode;
    }

    public DiscountCodeState getState() {
        return state;
    }
}
