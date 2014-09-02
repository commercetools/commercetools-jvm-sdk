package io.sphere.sdk.products.attributeaccess;

import io.sphere.sdk.products.AttributeAccessor;
import io.sphere.sdk.products.AttributeTypes;
import io.sphere.sdk.products.Product;

public interface WithColor {
    default AttributeAccessor<Product, String> hexColor() {
        return AttributeTypes.ofString().access("hex-color");
    }

    default AttributeAccessor<Product, Double> rgb() {
        return AttributeTypes.ofDouble().access("rgb");
    }
}
