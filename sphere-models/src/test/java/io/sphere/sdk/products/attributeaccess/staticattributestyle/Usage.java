package io.sphere.sdk.products.attributeaccess.staticattributestyle;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.attributeaccess.UsageBase;

import java.util.Optional;

public class Usage extends UsageBase {
    public void demo() {
        ProductVariant variant = getProductVariantSomeHow();
        Optional<LocalizedStrings> longDescription = variant.getAttribute(TShirt.LONG_DESCRIPTION);
    }
}
