package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public final class ShippingCostTarget extends Base implements CartDiscountTarget {
    @JsonCreator
    private ShippingCostTarget() {
    }

    public static ShippingCostTarget of() {
        return new ShippingCostTarget();
    }
}
