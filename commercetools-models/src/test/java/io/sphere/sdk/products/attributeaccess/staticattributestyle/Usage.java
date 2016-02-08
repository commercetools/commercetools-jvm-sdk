package io.sphere.sdk.products.attributeaccess.staticattributestyle;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.attributeaccess.UsageBase;

import java.util.Optional;

public class Usage extends UsageBase {
    public void demo() {
        ProductVariant variant = getProductVariantSomeHow();
        Optional<LocalizedString> longDescription = variant.findAttribute(TShirt.LONG_DESCRIPTION);
    }
}
