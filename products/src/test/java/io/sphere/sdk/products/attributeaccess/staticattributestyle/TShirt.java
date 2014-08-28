package io.sphere.sdk.products.attributeaccess.staticattributestyle;

import io.sphere.sdk.models.AttributeAccessor;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import static io.sphere.sdk.models.AttributeAccessor.*;

public class TShirt {
    public static final AttributeAccessor<Product, LocalizedString> LONG_DESCRIPTION = ofLocalizedString("longDescription");
}
