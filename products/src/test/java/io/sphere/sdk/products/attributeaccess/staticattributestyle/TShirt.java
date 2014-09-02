package io.sphere.sdk.products.attributeaccess.staticattributestyle;

import io.sphere.sdk.products.AttributeAccessor;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.AttributeTypes;
import io.sphere.sdk.products.Product;

public class TShirt {
    public static final AttributeAccessor<Product, LocalizedString> LONG_DESCRIPTION = AttributeTypes.ofLocalizedString().access("longDescription");
}
