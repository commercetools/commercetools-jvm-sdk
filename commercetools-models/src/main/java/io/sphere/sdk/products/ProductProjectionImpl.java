package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceViewImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.reviews.ReviewRatingStatistics;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;


class ProductProjectionImpl extends ResourceViewImpl<ProductProjection, Product> implements ProductProjection {
    private final Reference<ProductType> productType;
    @Nullable
    private final Reference<State> state;
    @Nullable
    private final Reference<TaxCategory> taxCategory;
    @JsonProperty("published")
    private final Boolean isPublished;
    @JsonProperty("hasStagedChanges")
    private final Boolean hasStagedChanges;
    private final LocalizedString name;
    private final Set<Reference<Category>> categories;
    @Nullable
    private final LocalizedString description;
    private final LocalizedString slug;
    @Nullable
    private final LocalizedString metaTitle;
    @Nullable
    private final LocalizedString metaDescription;
    @Nullable
    private final LocalizedString metaKeywords;
    private final ProductVariant masterVariant;
    private final List<ProductVariant> variants;
    private final SearchKeywords searchKeywords;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final CategoryOrderHints categoryOrderHints;
    @Nullable
    private final ReviewRatingStatistics reviewRatingStatistics;
    @Nullable
    private final String key;

    @JsonCreator
    ProductProjectionImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                          final Reference<ProductType> productType, @Nullable final Reference<State> state, @Nullable final Reference<TaxCategory> taxCategory,
                          final Boolean hasStagedChanges, final LocalizedString name,
                          final Set<Reference<Category>> categories, @Nullable final LocalizedString description,
                          final LocalizedString slug, @Nullable final LocalizedString metaTitle,
                          @Nullable final LocalizedString metaDescription, @Nullable final LocalizedString metaKeywords,
                          final ProductVariant masterVariant, final List<ProductVariant> variants,
                          final Boolean isPublished, final SearchKeywords searchKeywords,
                          @Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) final CategoryOrderHints categoryOrderHints,
                          @Nullable final ReviewRatingStatistics reviewRatingStatistics, @Nullable final String key) {
        super(id, version, createdAt, lastModifiedAt);
        this.productType = productType;
        this.state = state;
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
        this.categoryOrderHints = categoryOrderHints;
        this.reviewRatingStatistics = reviewRatingStatistics;
        this.key = key;
        getAllVariants().stream()
                .filter(v -> v instanceof ProductVariantImpl)
                .forEach(variant -> ((ProductVariantImpl)variant).setProductId(getId()));
    }

    public Boolean hasStagedChanges() {
        return hasStagedChanges;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public Set<Reference<Category>> getCategories() {
        return categories;
    }

    @Override
    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Override
    public LocalizedString getSlug() {
        return slug;
    }

    @Override
    @Nullable
    public LocalizedString getMetaTitle() {
        return metaTitle;
    }

    @Override
    @Nullable
    public LocalizedString getMetaDescription() {
        return metaDescription;
    }

    @Override
    @Nullable
    public LocalizedString getMetaKeywords() {
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

    @Override
    @Nullable
    public CategoryOrderHints getCategoryOrderHints() {
        return categoryOrderHints;
    }

    @Override
    @Nullable
    public ReviewRatingStatistics getReviewRatingStatistics() {
        return reviewRatingStatistics;
    }

    @Override
    @Nullable
    public Reference<State> getState() {
        return state;
    }

    @Override
    @Nullable
    public String getKey() {
        return key;
    }
}
