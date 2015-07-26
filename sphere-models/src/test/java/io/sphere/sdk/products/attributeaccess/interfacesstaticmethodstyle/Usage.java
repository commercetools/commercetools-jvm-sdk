package io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.attributeaccess.UsageBase;

import java.util.Optional;

public class Usage extends UsageBase {
    public void demo() {
        ProductVariant variant = getProductVariantSomeHow();
        Optional<LocalizedStrings> longDescription = variant.findAttribute(TShirt.attributes().longDescription());
    }
}
