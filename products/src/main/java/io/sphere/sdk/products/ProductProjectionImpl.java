package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.DefaultModelViewImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


class ProductProjectionImpl extends DefaultModelViewImpl<ProductProjection> implements ProductProjection {
    private final Reference<ProductType> productType;
    private final Optional<Reference<TaxCategory>> taxCategory;
    @JsonProperty("hasStagedChanges")
    private final boolean hasStagedChanges;
    private final LocalizedString name;
    private final List<Reference<Category>> categories;
    private final Optional<LocalizedString> description;
    private final LocalizedString slug;
    private final Optional<LocalizedString> metaTitle;
    private final Optional<LocalizedString> metaDescription;
    private final Optional<LocalizedString> metaKeywords;
    private final ProductVariant masterVariant;
    private final List<ProductVariant> variants;

    @JsonCreator
    ProductProjectionImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt,
                          final Reference<ProductType> productType, final Optional<Reference<TaxCategory>> taxCategory,
                          final boolean hasStagedChanges, final LocalizedString name,
                          final List<Reference<Category>> categories, final Optional<LocalizedString> description,
                          final LocalizedString slug, final Optional<LocalizedString> metaTitle,
                          final Optional<LocalizedString> metaDescription, final Optional<LocalizedString> metaKeywords,
                          final ProductVariant masterVariant, final List<ProductVariant> variants) {
        super(id, version, createdAt, lastModifiedAt);
        this.productType = productType;
        this.taxCategory = taxCategory;
        this.hasStagedChanges = hasStagedChanges;
        this.name = name;
        this.categories = categories;
        this.description = description;
        this.slug = slug;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
        this.masterVariant = masterVariant;
        this.variants = variants;
    }

    public boolean hasStagedChanges() {
        return hasStagedChanges;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public List<Reference<Category>> getCategories() {
        return categories;
    }

    @Override
    public Optional<LocalizedString> getDescription() {
        return description;
    }

    @Override
    public LocalizedString getSlug() {
        return slug;
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
    public ProductVariant getMasterVariant() {
        return masterVariant;
    }

    @Override
    public List<ProductVariant> getVariants() {
        return variants;
    }

    @Override
    public Reference<ProductType> getProductType() {
        return productType;
    }

    @Override
    public Optional<Reference<TaxCategory>> getTaxCategory() {
        return taxCategory;
    }
}
