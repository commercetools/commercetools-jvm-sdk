package io.sphere.sdk.cartdiscounts;

import io.sphere.sdk.models.Base;

public class ShippingCostTarget extends Base implements CartDiscountTarget {
    private ShippingCostTarget() {
    }

    public static ShippingCostTarget of() {
        return new ShippingCostTarget();
    }
}
