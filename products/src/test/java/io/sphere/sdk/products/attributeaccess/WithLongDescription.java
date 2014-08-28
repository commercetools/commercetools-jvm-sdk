package io.sphere.sdk.products.attributeaccess;

import io.sphere.sdk.models.AttributeAccessor;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

public interface WithLongDescription {
    default AttributeAccessor<Product, LocalizedString> longDescription() {
        return AttributeAccessor.ofLocalizedString("longDescription");
    }
}
