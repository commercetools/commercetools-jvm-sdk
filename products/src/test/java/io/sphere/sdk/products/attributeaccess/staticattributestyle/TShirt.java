package io.sphere.sdk.products.attributeaccess.staticattributestyle;

import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.attributes.TypeSafeAttributeAccess;
import io.sphere.sdk.products.Product;

public class TShirt {
    public static final AttributeGetterSetter<Product, LocalizedString> LONG_DESCRIPTION = TypeSafeAttributeAccess.ofLocalizedString().getterSetter("longDescription");
}
