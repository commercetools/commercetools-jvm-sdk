package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.products.attributeaccess.*;

public final class TShirt implements WithPromoMoney, WithLongDescription, WithColor {
    public static TShirt attributes() {
        return new TShirt();
    }
}
