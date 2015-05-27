package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.AttributeGetterSetter;

public interface WithColor {
    default AttributeGetterSetter<String> hexColor() {
        return AttributeAccess.ofString().ofName("hex-color");
    }

    default AttributeGetterSetter<Double> rgb() {
        return AttributeAccess.ofDouble().ofName("rgb");
    }
}
