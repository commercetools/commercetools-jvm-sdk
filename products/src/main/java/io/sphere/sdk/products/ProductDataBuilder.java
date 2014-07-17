package io.sphere.sdk.products;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;

public class ProductDataBuilder extends ProductDataNewProductBuilderBase<ProductDataBuilder> implements Builder<ProductData> {

    private NewProductVariant masterVariant;

    private ProductDataBuilder(LocalizedString name, LocalizedString slug, NewProductVariant masterVariant) {
        super(name, slug);
        this.masterVariant = masterVariant;
    }

    public static ProductDataBuilder of(LocalizedString name, LocalizedString slug, NewProductVariant masterVariant) {
        return new ProductDataBuilder(name, slug, masterVariant);
    }

    @Override
    protected ProductDataBuilder getThis() {
        return this;
    }

    @Override
    public ProductData build() {
        return new ProductDataImpl(getName(), getCategories(), getDescription(), getSlug(), getMetaTitle(), getMetaDescription(), getMetaKeywords(), masterVariant, getVariants());
    }
}
