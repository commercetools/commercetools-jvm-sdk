package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

class ProductToProductProjectionWrapper implements ProductProjection {
    private final Product product;
    private final ProductData productData;

    ProductToProductProjectionWrapper(final Product product, final ProductProjectionType productProjectionType) {
        this.product = product;
        this.productData = product.getMasterData().get(productProjectionType);
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
    public LocalizedString getName() {
        return productData.getName();
    }

    @Override
    public List<Reference<Category>> getCategories() {
        return productData.getCategories();
    }

    @Override
    public Optional<LocalizedString> getDescription() {
        return productData.getDescription();
    }

    @Override
    public LocalizedString getSlug() {
        return productData.getSlug();
    }

    @Override
    public Optional<LocalizedString> getMetaTitle() {
        return productData.getMetaTitle();
    }

    @Override
    public Optional<LocalizedString> getMetaDescription() {
        return productData.getMetaDescription();
    }

    @Override
    public Optional<LocalizedString> getMetaKeywords() {
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
    public Instant getCreatedAt() {
        return product.getCreatedAt();
    }

    @Override
    public Instant getLastModifiedAt() {
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
}
