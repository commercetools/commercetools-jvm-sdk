package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class ProductToProductProjectionWrapper implements ProductProjection {
    private final Product product;
    private final ProductData productData;

    ProductToProductProjectionWrapper(final Product product, final ProductProjectionType productProjectionType) {
        this.product = product;
        this.productData = product.getMasterData().get(productProjectionType).get();
    }

    @Override
    public boolean hasStagedChanges() {
        return product.getMasterData().hasStagedChanges();
    }

    @Override
    public String getId() {
        return product.getId();
    }

    @Override
    public LocalizedStrings getName() {
        return productData.getName();
    }

    @Override
    public Set<Reference<Category>> getCategories() {
        return productData.getCategories();
    }

    @Override
    public Optional<LocalizedStrings> getDescription() {
        return productData.getDescription();
    }

    @Override
    public LocalizedStrings getSlug() {
        return productData.getSlug();
    }

    @Override
    public LocalizedStrings getMetaTitle() {
        return productData.getMetaTitle();
    }

    @Override
    public LocalizedStrings getMetaDescription() {
        return productData.getMetaDescription();
    }

    @Override
    public LocalizedStrings getMetaKeywords() {
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
    public Optional<Reference<TaxCategory>> getTaxCategory() {
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
    public long getVersion() {
        return product.getVersion();
    }

    @Override
    public boolean isPublished() {
        return product.getMasterData().isPublished();
    }

    @Override
    public SearchKeywords getSearchKeywords() {
        return productData.getSearchKeywords();
    }
}
