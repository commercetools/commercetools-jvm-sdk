package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

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
