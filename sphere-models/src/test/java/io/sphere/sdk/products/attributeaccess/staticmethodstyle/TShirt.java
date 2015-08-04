package io.sphere.sdk.products.attributeaccess.staticmethodstyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.NamedAttributeAccess;
import io.sphere.sdk.models.LocalizedString;

public class TShirt {
    public static NamedAttributeAccess<LocalizedString> longDescription() {
        return AttributeAccess.ofLocalizedStrings().ofName("longDescription");
    }
}
