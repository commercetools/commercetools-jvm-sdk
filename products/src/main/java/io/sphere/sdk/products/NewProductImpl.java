package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

class NewProductImpl extends Base implements NewProduct {
    private final Reference<ProductType> productType;
    private final LocalizedString name;
    private final LocalizedString slug;
    private final Optional<LocalizedString> description;
    private final List<Reference<Category>> categories;
    private final Optional<LocalizedString> metaTitle;
    private final Optional<LocalizedString> metaDescription;
    private final Optional<LocalizedString> metaKeywords;
    private final Optional<NewProductVariant> masterVariant;
    private final List<NewProductVariant> variants;

    public NewProductImpl(final Reference<ProductType> productType, final LocalizedString name, final LocalizedString slug,
                          final Optional<LocalizedString> description, final List<Reference<Category>> categories,
                          final MetaAttributes metaAttributes, final Optional<NewProductVariant> masterVariant,
                          final List<NewProductVariant> variants) {
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
    public LocalizedString getName() {
        return name;
    }

    @Override
    public LocalizedString getSlug() {
        return slug;
    }

    @Override
    public Optional<LocalizedString> getDescription() {
        return description;
    }

    @Override
    public List<Reference<Category>> getCategories() {
        return categories;
    }

    @Override
    public Optional<LocalizedString> getMetaTitle() {
        return metaTitle;
    }

    @Override
    public Optional<LocalizedString> getMetaDescription() {
        return metaDescription;
    }

    @Override
    public Optional<LocalizedString> getMetaKeywords() {
        return metaKeywords;
    }

    @Override
    public Optional<NewProductVariant> getMasterVariant() {
        return masterVariant;
    }

    @Override
    public List<NewProductVariant> getVariants() {
        return variants;
    }
}
