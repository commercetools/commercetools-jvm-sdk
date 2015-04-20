package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.DefaultModelViewImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;


class ProductProjectionImpl extends DefaultModelViewImpl<ProductProjection> implements ProductProjection {
    private final Reference<ProductType> productType;
    private final Optional<Reference<TaxCategory>> taxCategory;
    @JsonProperty("published")
    private final boolean isPublished;
    @JsonProperty("hasStagedChanges")
    private final boolean hasStagedChanges;
    private final LocalizedStrings name;
    private final Set<Reference<Category>> categories;
    private final Optional<LocalizedStrings> description;
    private final LocalizedStrings slug;
    private final Optional<LocalizedStrings> metaTitle;
    private final Optional<LocalizedStrings> metaDescription;
    private final Optional<LocalizedStrings> metaKeywords;
    private final ProductVariant masterVariant;
    private final List<ProductVariant> variants;
    private final SearchKeywords searchKeywords;

    @JsonCreator
    ProductProjectionImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt,
                          final Reference<ProductType> productType, final Optional<Reference<TaxCategory>> taxCategory,
                          final boolean hasStagedChanges, final LocalizedStrings name,
                          final Set<Reference<Category>> categories, final Optional<LocalizedStrings> description,
                          final LocalizedStrings slug, final Optional<LocalizedStrings> metaTitle,
                          final Optional<LocalizedStrings> metaDescription, final Optional<LocalizedStrings> metaKeywords,
                          final ProductVariant masterVariant, final List<ProductVariant> variants,
                          final boolean isPublished, final SearchKeywords searchKeywords) {
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
        this.isPublished = isPublished;
        this.searchKeywords = searchKeywords;
    }

    public boolean hasStagedChanges() {
        return hasStagedChanges;
    }

    @Override
    public LocalizedStrings getName() {
        return name;
    }

    @Override
    public Set<Reference<Category>> getCategories() {
        return categories;
    }

    @Override
    public Optional<LocalizedStrings> getDescription() {
        return description;
    }

    @Override
    public LocalizedStrings getSlug() {
        return slug;
    }

    @Override
    public Optional<LocalizedStrings> getMetaTitle() {
        return metaTitle;
    }

    @Override
    public Optional<LocalizedStrings> getMetaDescription() {
        return metaDescription;
    }

    @Override
    public Optional<LocalizedStrings> getMetaKeywords() {
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

    @Override
    public boolean isPublished() {
        return isPublished;
    }

    @Override
    public SearchKeywords getSearchKeywords() {
        return searchKeywords;
    }
}
