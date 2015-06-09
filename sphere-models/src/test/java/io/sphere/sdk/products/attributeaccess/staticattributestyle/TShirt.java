package io.sphere.sdk.products.attributeaccess.staticattributestyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.NamedAttributeAccess;
import io.sphere.sdk.models.LocalizedStrings;

public class TShirt {
    public static final NamedAttributeAccess<LocalizedStrings> LONG_DESCRIPTION = AttributeAccess.ofLocalizedStrings().ofName("longDescription");
}
