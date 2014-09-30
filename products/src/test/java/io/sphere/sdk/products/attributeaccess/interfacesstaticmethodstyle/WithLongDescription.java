package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

public interface WithLongDescription {
    default AttributeGetterSetter<Product, LocalizedString> longDescription() {
        return AttributeAccess.ofLocalizedString().getterSetter("longDescription");
    }
}
