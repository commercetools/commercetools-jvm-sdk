package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.List;
import java.util.Optional;

/**
 * For construction in unit tests use {@link io.sphere.sdk.products.ProductDataBuilder}.
 */
@JsonDeserialize(as=ProductDataImpl.class)
public interface ProductData extends ProductDataLike {
    @Override
    public LocalizedStrings getName();

    @Override
    public List<Reference<Category>> getCategories();

    @Override
    public Optional<LocalizedStrings> getDescription();

    @Override
    public LocalizedStrings getSlug();

    @Override
    public Optional<LocalizedStrings> getMetaTitle();

    @Override
    public Optional<LocalizedStrings> getMetaDescription();

    @Override
    public Optional<LocalizedStrings> getMetaKeywords();

    @Override
    public ProductVariant getMasterVariant();

    @Override
    public List<ProductVariant> getVariants();

    @Override
    default List<ProductVariant> getAllVariants() {
        return ProductsPackage.getAllVariants(this);
    }

    @Override
    default Optional<ProductVariant> getVariant(final int variantId){
        return ProductsPackage.getVariant(variantId, this);
    }

    @Override
    default ProductVariant getVariantOrMaster(final int variantId) {
        return ProductsPackage.getVariantOrMaster(variantId, this);
    }
}
