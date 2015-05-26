package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.models.LocalizedStrings;

public interface WithLongDescription {
    default AttributeGetterSetter<LocalizedStrings> longDescription() {
        return AttributeAccess.ofLocalizedStrings().ofName("longDescription");
    }
}
