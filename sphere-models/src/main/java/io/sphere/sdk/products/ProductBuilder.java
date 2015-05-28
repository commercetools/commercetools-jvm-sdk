package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.models.DefaultModelFluentBuilder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.taxcategories.TaxCategory;

import static java.util.Objects.requireNonNull;

/**
 Builds test doubles1 for products.

 <p>Example:</p>

 {@include.example io.sphere.sdk.products.ProductBuilderTest#demoUsage()}

 */
public class ProductBuilder extends DefaultModelFluentBuilder<ProductBuilder, Product> {
    private final Reference<ProductType> productType;
    private final ProductCatalogData masterData;
    private Optional<Reference<TaxCategory>> taxCategory = Optional.empty();

    private ProductBuilder(final Reference<ProductType> productType, final ProductCatalogData masterData) {
        this.productType = productType;
        this.masterData = masterData;
    }

    public static ProductBuilder of(final Referenceable<ProductType> productType, final ProductCatalogData masterData) {
        return new ProductBuilder(productType.toReference(), requireNonNull(masterData));
    }

    public ProductBuilder taxCategory(final Optional<Reference<TaxCategory>> taxCategory) {
        this.taxCategory = taxCategory;
        return getThis();
    }

    public ProductBuilder taxCategory(final Referenceable<TaxCategory> taxCategory) {
        return taxCategory(Optional.of(taxCategory.toReference()));
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
