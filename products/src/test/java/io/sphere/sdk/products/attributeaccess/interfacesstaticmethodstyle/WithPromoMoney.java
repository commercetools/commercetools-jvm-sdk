package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.products.Product;

import javax.money.MonetaryAmount;

public interface WithPromoMoney {
    default AttributeGetterSetter<Product, MonetaryAmount> promoMoney() {
        return AttributeAccess.ofMoney().getterSetter("promo-money");
    }
}
