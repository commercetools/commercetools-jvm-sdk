package io.sphere.sdk.products;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.producttypes.ProductType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;

public final class ProductDraftBuilder extends ProductDataProductDraftBuilderBase<ProductDraftBuilder> implements Builder<ProductDraft> {

    private final ResourceIdentifier<ProductType> productType;
    private ProductVariantDraft masterVariant;
    private List<ProductVariantDraft> variants = Collections.emptyList();

    private ProductDraftBuilder(final ResourceIdentifier<ProductType> productType, final LocalizedString name, final LocalizedString slug, final ProductVariantDraft masterVariant) {
        super(name, slug);
        this.productType = productType;
        this.masterVariant = masterVariant;
    }

    public static ProductDraftBuilder of(final ProductDraft productDraft) {
        return of(productDraft.getProductType(), productDraft.getName(), productDraft.getSlug(), productDraft.getMasterVariant())
                .variants(productDraft.getVariants());
    }

    public static ProductDraftBuilder of(final ResourceIdentifiable<ProductType> productType, final LocalizedString name, final LocalizedString slug, final ProductVariantDraft masterVariant) {
        return new ProductDraftBuilder(productType.toResourceIdentifier(), name, slug, masterVariant);
    }

    @Override
    public ProductDraft build() {
        return new ProductDraftImpl(productType, getName(), getSlug(), getDescription(), getCategories(), getMetaTitle(), getMetaDescription(), getMetaKeywords(), masterVariant, variants, getTaxCategory(), getSearchKeywords(), getState(), getCategoryOrderHints());
    }

    @Override
    protected ProductDraftBuilder getThis() {
        return this;
    }

    public ProductDraftBuilder variants(final List<ProductVariantDraft> variants) {
        this.variants = variants != null
                ? Collections.unmodifiableList(new ArrayList<>(variants))
                : Collections.emptyList();
        return getThis();
    }

    public ProductDraftBuilder plusVariants(final ProductVariantDraft variantToAdd) {
        return variants(listOf(this.variants, variantToAdd));
    }

    public ProductDraftBuilder plusVariants(final List<ProductVariantDraft> variantsToAdd) {
        return variants(listOf(this.variants, variantsToAdd));
    }

    public ProductDraftBuilder masterVariant(final ProductVariantDraft masterVariant) {
        this.masterVariant = masterVariant;
        return getThis();
    }
}
