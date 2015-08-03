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

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;


class ProductProjectionImpl extends DefaultModelViewImpl<ProductProjection> implements ProductProjection {
    private final Reference<ProductType> productType;
    @Nullable
    private final Reference<TaxCategory> taxCategory;
    @JsonProperty("published")
    private final Boolean isPublished;
    @JsonProperty("hasStagedChanges")
    private final Boolean hasStagedChanges;
    private final LocalizedStrings name;
    private final Set<Reference<Category>> categories;
    @Nullable
    private final LocalizedStrings description;
    private final LocalizedStrings slug;
    @Nullable
    private final LocalizedStrings metaTitle;
    @Nullable
    private final LocalizedStrings metaDescription;
    @Nullable
    private final LocalizedStrings metaKeywords;
    private final ProductVariant masterVariant;
    private final List<ProductVariant> variants;
    private final SearchKeywords searchKeywords;

    @JsonCreator
    ProductProjectionImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                          final Reference<ProductType> productType, @Nullable final Reference<TaxCategory> taxCategory,
                          final Boolean hasStagedChanges, final LocalizedStrings name,
                          final Set<Reference<Category>> categories, final LocalizedStrings description,
                          final LocalizedStrings slug, final LocalizedStrings metaTitle,
                          final LocalizedStrings metaDescription, final LocalizedStrings metaKeywords,
                          final ProductVariant masterVariant, final List<ProductVariant> variants,
                          final Boolean isPublished, final SearchKeywords searchKeywords) {
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
        getAllVariants().stream()
                .filter(v -> v instanceof ProductVariantImpl)
                .forEach(variant -> ((ProductVariantImpl)variant).setProductId(getId()));
    }

    public Boolean hasStagedChanges() {
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
    @Nullable
    public LocalizedStrings getDescription() {
        return description;
    }

    @Override
    public LocalizedStrings getSlug() {
        return slug;
    }

    @Override
    @Nullable
    public LocalizedStrings getMetaTitle() {
        return metaTitle;
    }

    @Override
    @Nullable
    public LocalizedStrings getMetaDescription() {
        return metaDescription;
    }

    @Override
    @Nullable
    public LocalizedStrings getMetaKeywords() {
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
    @Nullable
    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Override
    public Boolean isPublished() {
        return isPublished;
    }

    @Override
    public SearchKeywords getSearchKeywords() {
        return searchKeywords;
    }
}
