package io.sphere.sdk.products.attributeaccess.staticmethodstyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.Product;

public class TShirt {
    public static AttributeGetterSetter<Product, LocalizedStrings> longDescription() {
        return AttributeAccess.ofLocalizedStrings().ofName("longDescription");
    }
}
