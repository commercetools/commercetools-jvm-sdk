package io.sphere.sdk.products.attributeaccess.staticattributestyle;

import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.models.LocalizedString;

public class TShirt {
    public static final NamedAttributeAccess<LocalizedString> LONG_DESCRIPTION = AttributeAccess.ofLocalizedString().ofName("longDescription");
}
