package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.search.SearchKeywords;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@ResourceValue(abstractResourceClass = true)
@JsonDeserialize(as=ProductDataImpl.class)
public interface ProductData extends ProductDataLike {
    @Override
    LocalizedString getName();

    @Override
    Set<Reference<Category>> getCategories();

    @Override
    @Nullable
    LocalizedString getDescription();

    @Override
    LocalizedString getSlug();

    @Nullable
    @Override
    LocalizedString getMetaTitle();

    @Nullable
    @Override
    LocalizedString getMetaDescription();

    @Nullable
    @Override
    LocalizedString getMetaKeywords();

    @Override
    ProductVariant getMasterVariant();

    @Override
    List<ProductVariant> getVariants();

    @Override
    default List<ProductVariant> getAllVariants() {
        return ProductsPackage.getAllVariants(this);
    }

    @Override
    @Nullable
    default ProductVariant getVariant(final int variantId){
        return ProductsPackage.getVariant(variantId, this).orElse(null);
    }

    @Override
    default ProductVariant getVariantOrMaster(final int variantId) {
        return ProductsPackage.getVariantOrMaster(variantId, this);
    }

    @Override
    SearchKeywords getSearchKeywords();

    @Nullable
    CategoryOrderHints getCategoryOrderHints();
}
