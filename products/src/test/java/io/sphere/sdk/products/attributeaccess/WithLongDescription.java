package io.sphere.sdk.products.attributeaccess;

import io.sphere.sdk.products.AttributeAccessor;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.AttributeTypes;
import io.sphere.sdk.products.Product;

public interface WithLongDescription {
    default AttributeAccessor<Product, LocalizedString> longDescription() {
        return AttributeTypes.ofLocalizedString().access("longDescription");
    }
}
