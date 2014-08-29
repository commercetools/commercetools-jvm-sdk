package io.sphere.sdk.products.attributeaccess.staticmethodstyle;

import io.sphere.sdk.models.AttributeAccessor;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import static io.sphere.sdk.models.AttributeAccessor.*;

public class TShirt {
    public static AttributeAccessor<Product, LocalizedString> longDescription() {
        return ofLocalizedString("longDescription");
    }
}
