package io.sphere.sdk.products;

import com.google.common.base.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Collections;
import java.util.List;

public abstract class BaseNewProduct extends Base implements NewProduct {
    private final Reference<ProductType> productType;

    public BaseNewProduct(final Reference<ProductType> productType) {
        this.productType = productType;
    }

    @Override
    public Reference<ProductType> getProductType() {
        return productType;
    }
}
