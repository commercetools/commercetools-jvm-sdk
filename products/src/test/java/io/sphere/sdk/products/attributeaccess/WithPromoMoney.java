package io.sphere.sdk.products.attributeaccess;

import io.sphere.sdk.models.AttributeAccessor;
import io.sphere.sdk.models.Money;
import io.sphere.sdk.products.Product;

public interface WithPromoMoney {
    default AttributeAccessor<Product, Money> promoMoney() {
        return AttributeAccessor.ofMoney("promo-money");
    }
}
