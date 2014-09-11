package io.sphere.sdk.products.attributeaccess;

import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.attributes.AttributeTypes;
import io.sphere.sdk.products.Product;

public interface WithColor {
    default AttributeGetterSetter<Product, String> hexColor() {
        return AttributeTypes.ofString().getterSetter("hex-color");
    }

    default AttributeGetterSetter<Product, Double> rgb() {
        return AttributeTypes.ofDouble().getterSetter("rgb");
    }
}
