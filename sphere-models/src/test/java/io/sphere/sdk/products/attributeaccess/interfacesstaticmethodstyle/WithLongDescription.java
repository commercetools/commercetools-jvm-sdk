package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.NamedAttributeAccess;
import io.sphere.sdk.models.LocalizedString;

public interface WithLongDescription {
    default NamedAttributeAccess<LocalizedString> longDescription() {
        return AttributeAccess.ofLocalizedStrings().ofName("longDescription");
    }
}
