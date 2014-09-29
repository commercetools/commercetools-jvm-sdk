package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.models.Money;
import io.sphere.sdk.attributes.TypeSafeAttributeAccess;
import io.sphere.sdk.products.Product;

public interface WithPromoMoney {
    default AttributeGetterSetter<Product, Money> promoMoney() {
        return TypeSafeAttributeAccess.ofMoney().getterSetter("promo-money");
    }
}
