package io.sphere.sdk.products;

import com.google.common.base.Optional;
import io.sphere.sdk.models.DefaultModelFluentBuilder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.taxcategories.TaxCategory;

public class ProductBuilder extends DefaultModelFluentBuilder<ProductBuilder, Product> {
    private final Reference<ProductType> productType;
    private final ProductCatalogData masterData;
    private Optional<Reference<TaxCategory>> taxCategory = Optional.absent();

    private ProductBuilder(final Reference<ProductType> productType, final ProductCatalogData masterData) {
        this.productType = productType;
        this.masterData = masterData;
    }

    public static ProductBuilder of(final Reference<ProductType> productType, final ProductCatalogData masterData) {
        return new ProductBuilder(productType, masterData);
    }

    public ProductBuilder taxCategory(final Optional<Reference<TaxCategory>> taxCategory) {
        this.taxCategory = taxCategory;
        return getThis();
    }

    public ProductBuilder taxCategory(final Reference<TaxCategory> taxCategory) {
        return taxCategory(Optional.of(taxCategory));
    }

    @Override
    protected ProductBuilder getThis() {
        return this;
    }

    @Override
    public Product build() {
        return new ProductImpl(id, version, createdAt, lastModifiedAt, productType, masterData, taxCategory);
    }
}
