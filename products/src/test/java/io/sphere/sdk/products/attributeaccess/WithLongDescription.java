package io.sphere.sdk.products.attributeaccess;

import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.attributes.TypeSafeAttributeAccess;
import io.sphere.sdk.products.Product;

public interface WithLongDescription {
    default AttributeGetterSetter<Product, LocalizedString> longDescription() {
        return TypeSafeAttributeAccess.ofLocalizedString().getterSetter("longDescription");
    }
}
