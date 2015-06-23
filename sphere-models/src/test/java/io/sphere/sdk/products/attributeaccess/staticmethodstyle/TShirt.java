package io.sphere.sdk.products.attributeaccess.staticmethodstyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.NamedAttributeAccess;
import io.sphere.sdk.models.LocalizedStrings;

public class TShirt {
    public static NamedAttributeAccess<LocalizedStrings> longDescription() {
        return AttributeAccess.ofLocalizedStrings().ofName("longDescription");
    }
}
