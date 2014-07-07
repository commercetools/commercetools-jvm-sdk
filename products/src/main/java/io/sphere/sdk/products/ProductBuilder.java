package io.sphere.sdk.products;

import io.sphere.sdk.models.DefaultModelFluentBuilder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

public class ProductBuilder extends DefaultModelFluentBuilder<ProductBuilder, Product> {
    private Reference<ProductType> productType;

    public ProductBuilder(final Reference<ProductType> productType) {
        this.productType = productType;
    }

    public static ProductBuilder of(final Reference<ProductType> productType) {
        return new ProductBuilder(productType);
    }

    @Override
    protected ProductBuilder getThis() {
        return this;
    }

    @Override
    public Product build() {
        return new ProductImpl(id, version, createdAt, lastModifiedAt, productType);
    }

    public Reference<ProductType> getProductType() {
        return productType;
    }
}
