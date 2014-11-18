package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.products.Product;

public interface WithColor {
    default AttributeGetterSetter<Product, String> hexColor() {
        return AttributeAccess.ofString().getterSetter("hex-color");
    }

    default AttributeGetterSetter<Product, Double> rgb() {
        return AttributeAccess.ofDouble().getterSetter("rgb");
    }
}
