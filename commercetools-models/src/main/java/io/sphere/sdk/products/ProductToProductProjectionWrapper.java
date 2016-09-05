package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
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

class ProductToProductProjectionWrapper implements ProductProjection {
    private final Product product;
    private final ProductData productData;

    ProductToProductProjectionWrapper(final Product product, final ProductProjectionType productProjectionType) {
        this.product = product;
        this.productData = product.getMasterData().get(productProjectionType);
    }

    @Override
    public Boolean hasStagedChanges() {
        return product.getMasterData().hasStagedChanges();
    }

    @Override
    public String getId() {
        return product.getId();
    }

    @Override
    public LocalizedString getName() {
        return productData.getName();
    }

    @Override
    public Set<Reference<Category>> getCategories() {
        return productData.getCategories();
    }

    @Override
    @Nullable
    public LocalizedString getDescription() {
        return productData.getDescription();
    }

    @Override
    public LocalizedString getSlug() {
        return productData.getSlug();
    }

    @Override
    @Nullable
    public LocalizedString getMetaTitle() {
        return productData.getMetaTitle();
    }

    @Override
    @Nullable
    public LocalizedString getMetaDescription() {
        return productData.getMetaDescription();
    }

    @Override
    @Nullable
    public LocalizedString getMetaKeywords() {
        return productData.getMetaKeywords();
    }

    @Override
    public ProductVariant getMasterVariant() {
        return productData.getMasterVariant();
    }

    @Override
    public List<ProductVariant> getVariants() {
        return productData.getVariants();
    }

    @Override
    public Reference<ProductType> getProductType() {
        return product.getProductType();
    }

    @Override
    @Nullable
    public Reference<TaxCategory> getTaxCategory() {
        return product.getTaxCategory();
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return product.getCreatedAt();
    }

    @Override
    public ZonedDateTime getLastModifiedAt() {
        return product.getLastModifiedAt();
    }

    @Override
    public Long getVersion() {
        return product.getVersion();
    }

    @Override
    public Boolean isPublished() {
        return product.getMasterData().isPublished();
    }

    @Override
    public SearchKeywords getSearchKeywords() {
        return productData.getSearchKeywords();
    }

    @Nullable
    @Override
    public CategoryOrderHints getCategoryOrderHints() {
        return productData.getCategoryOrderHints();
    }

    @Nullable
    @Override
    public ReviewRatingStatistics getReviewRatingStatistics() {
        return product.getReviewRatingStatistics();
    }

    @Nullable
    @Override
    public Reference<State> getState() {
        return product.getState();
    }

    @Nullable
    @Override
    public String getKey() {
        return product.getKey();
    }
}
