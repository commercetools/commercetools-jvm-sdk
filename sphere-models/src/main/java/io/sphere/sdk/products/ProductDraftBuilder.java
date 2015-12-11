package io.sphere.sdk.products;

import io.sphere.sdk.models.*;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Collections;
import java.util.List;

public final class ProductDraftBuilder extends ProductDataProductDraftBuilderBase<ProductDraftBuilder> implements Builder<ProductDraft> {

    private final ResourceIdentifier<ProductType> productType;
    private ProductVariantDraft masterVariant;
    private List<ProductVariantDraft> variants = Collections.emptyList();

    private ProductDraftBuilder(final ResourceIdentifier<ProductType> productType, final LocalizedString name, final LocalizedString slug, final ProductVariantDraft masterVariant) {
        super(name, slug);
        this.productType = productType;
        this.masterVariant = masterVariant;
    }

    public static ProductDraftBuilder of(final ResourceIdentifiable<ProductType> productType, final LocalizedString name, final LocalizedString slug, final ProductVariantDraft masterVariant) {
        return new ProductDraftBuilder(productType.toResourceIdentifier(), name, slug, masterVariant);
    }

    @Override
    public ProductDraft build() {
        return new ProductDraftImpl(productType, getName(), getSlug(), getDescription(), getCategories(), MetaAttributes.metaAttributesOf(getMetaTitle(), getMetaDescription(), getMetaKeywords()), masterVariant, variants, getTaxCategory(), getSearchKeywords(), getState(), getCategoryOrderHints());
    }

    @Override
    protected ProductDraftBuilder getThis() {
        return this;
    }

    public ProductDraftBuilder variants(final List<ProductVariantDraft> variants) {
        this.variants = variants;
        return getThis();
    }

    public ProductDraftBuilder masterVariant(final ProductVariantDraft masterVariant) {
        this.masterVariant = masterVariant;
        return getThis();
    }
}
