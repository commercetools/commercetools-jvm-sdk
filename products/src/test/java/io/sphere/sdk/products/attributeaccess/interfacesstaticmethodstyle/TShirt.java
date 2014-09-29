package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

public final class TShirt implements WithPromoMoney, WithLongDescription, WithColor {
    public static TShirt attributes() {
        return new TShirt();
    }
}
