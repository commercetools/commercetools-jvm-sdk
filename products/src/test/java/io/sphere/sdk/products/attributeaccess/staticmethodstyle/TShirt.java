package io.sphere.sdk.products.attributeaccess.staticmethodstyle;

import io.sphere.sdk.products.AttributeAccessor;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.AttributeTypes;
import io.sphere.sdk.products.Product;

public class TShirt {
    public static AttributeAccessor<Product, LocalizedString> longDescription() {
        return AttributeTypes.ofLocalizedString().access("longDescription");
    }
}
