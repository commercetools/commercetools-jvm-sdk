package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.attributes.TypeSafeAttributeAccess;
import io.sphere.sdk.products.Product;

public interface WithColor {
    default AttributeGetterSetter<Product, String> hexColor() {
        return TypeSafeAttributeAccess.ofString().getterSetter("hex-color");
    }

    default AttributeGetterSetter<Product, Double> rgb() {
        return TypeSafeAttributeAccess.ofDouble().getterSetter("rgb");
    }
}
