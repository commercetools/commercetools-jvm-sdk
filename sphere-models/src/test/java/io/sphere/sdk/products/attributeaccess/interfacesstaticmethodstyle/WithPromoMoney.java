package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;

import javax.money.MonetaryAmount;

public interface WithPromoMoney {
    default NamedAttributeAccess<MonetaryAmount> promoMoney() {
        return AttributeAccess.ofMoney().ofName("promo-money");
    }
}
