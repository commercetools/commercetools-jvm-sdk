package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;

public interface WithColor {
    default NamedAttributeAccess<String> hexColor() {
        return AttributeAccess.ofString().ofName("hex-color");
    }

    default NamedAttributeAccess<Double> rgb() {
        return AttributeAccess.ofDouble().ofName("rgb");
    }
}
