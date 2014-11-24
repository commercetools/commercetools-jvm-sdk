package io.sphere.sdk.products;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStrings;

import java.util.Collections;
import java.util.List;

public class ProductDataBuilder extends ProductDataProductDraftBuilderBase<ProductDataBuilder> implements Builder<ProductData> {

    private ProductVariant masterVariant;
    private List<ProductVariant> variants = Collections.emptyList();

    private ProductDataBuilder(LocalizedStrings name, LocalizedStrings slug, ProductVariant masterVariant) {
        super(name, slug);
        this.masterVariant = masterVariant;
    }

    public static ProductDataBuilder of(LocalizedStrings name, LocalizedStrings slug, ProductVariant masterVariant) {
        return new ProductDataBuilder(name, slug, masterVariant);
    }

    @Override
    protected ProductDataBuilder getThis() {
        return this;
    }

    @Override
    public ProductData build() {
        return new ProductDataImpl(getName(), getCategories(), getDescription(), getSlug(), getMetaTitle(), getMetaDescription(), getMetaKeywords(), masterVariant, variants);
    }

    public ProductDataBuilder variants(final List<ProductVariant> variants) {
        this.variants = variants;
        return getThis();
    }
}
