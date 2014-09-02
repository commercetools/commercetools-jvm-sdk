package io.sphere.sdk.products.attributeaccess;

import io.sphere.sdk.products.AttributeAccessor;
import io.sphere.sdk.models.Money;
import io.sphere.sdk.products.AttributeTypes;
import io.sphere.sdk.products.Product;

public interface WithPromoMoney {
    default AttributeAccessor<Product, Money> promoMoney() {
        return AttributeTypes.ofMoney().access("promo-money");
    }
}
