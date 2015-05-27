package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.AttributeGetterSetter;

import javax.money.MonetaryAmount;

public interface WithPromoMoney {
    default AttributeGetterSetter<MonetaryAmount> promoMoney() {
        return AttributeAccess.ofMoney().ofName("promo-money");
    }
}
