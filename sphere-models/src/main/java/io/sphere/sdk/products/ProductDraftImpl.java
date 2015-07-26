package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

class ProductDraftImpl extends Base implements ProductDraft {
    private final Reference<ProductType> productType;
    private final LocalizedStrings name;
    private final LocalizedStrings slug;
    @Nullable
    private final LocalizedStrings description;
    private final Set<Reference<Category>> categories;
    @Nullable
    private final LocalizedStrings metaTitle;
    @Nullable
    private final LocalizedStrings metaDescription;
    @Nullable
    private final LocalizedStrings metaKeywords;
    private final ProductVariantDraft masterVariant;
    private final List<ProductVariantDraft> variants;

    public ProductDraftImpl(final Reference<ProductType> productType, final LocalizedStrings name, final LocalizedStrings slug,
                            final LocalizedStrings description, final Set<Reference<Category>> categories,
                            final MetaAttributes metaAttributes, final ProductVariantDraft masterVariant,
                            final List<ProductVariantDraft> variants) {
        this.name = name;
        this.productType = productType;
        this.slug = slug;
        this.description = description;
        this.categories = categories;
        this.metaTitle = metaAttributes.getMetaTitle();
        this.metaDescription = metaAttributes.getMetaTitle();
        this.metaKeywords = metaAttributes.getMetaKeywords();
        this.masterVariant = masterVariant;
        this.variants = variants;
    }

    @Override
    public Reference<ProductType> getProductType() {
        return productType;
    }

    @Override
    public LocalizedStrings getName() {
        return name;
    }

    @Override
    public LocalizedStrings getSlug() {
        return slug;
    }

    @Override
    @Nullable
    public LocalizedStrings getDescription() {
        return description;
    }

    @Override
    public Set<Reference<Category>> getCategories() {
        return categories;
    }

    @Nullable
    @Override
    public LocalizedStrings getMetaTitle() {
        return metaTitle;
    }

    @Nullable
    @Override
    public LocalizedStrings getMetaDescription() {
        return metaDescription;
    }

    @Nullable
    @Override
    public LocalizedStrings getMetaKeywords() {
        return metaKeywords;
    }

    @Override
    public ProductVariantDraft getMasterVariant() {
        return masterVariant;
    }

    @Override
    public List<ProductVariantDraft> getVariants() {
        return variants;
    }
}
