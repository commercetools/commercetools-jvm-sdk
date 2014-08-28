package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.products.attributeaccess.WithColor;
import io.sphere.sdk.products.attributeaccess.WithLongDescription;
import io.sphere.sdk.products.attributeaccess.WithPromoMoney;

public final class TShirt implements WithPromoMoney, WithLongDescription, WithColor {
    private TShirt() {
    }

    public static TShirt attributes() {
        return new TShirt();
    }
}
