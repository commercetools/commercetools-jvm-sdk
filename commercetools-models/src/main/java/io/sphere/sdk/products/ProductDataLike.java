package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;
import io.sphere.sdk.products.commands.updateactions.ChangeMasterVariant;
import io.sphere.sdk.search.SearchKeywords;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

interface ProductDataLike extends WithLocalizedSlug, MetaAttributes {
    LocalizedString getName();

    Set<Reference<Category>> getCategories();

    @Nullable
    LocalizedString getDescription();

    LocalizedString getSlug();

    @Nullable
    LocalizedString getMetaTitle();

    @Nullable
    LocalizedString getMetaDescription();

    @Nullable
    LocalizedString getMetaKeywords();

    /**
     * Returns the master variant. Every product as 1 to n variants, so this is always present.
     *
     * @see #getAllVariants()
     * @see #getVariants()
     * @see ChangeMasterVariant
     * @return the main variant in the product
     */
    ProductVariant getMasterVariant();

    /**
     * Gets the variants which are not identical to the master variant. This list can be empty.
     *
     * @see #getAllVariants()
     * @see #getMasterVariant()
     *
     *
     * @return all variants except the one in {@link #getMasterVariant()}
     */
    List<ProductVariant> getVariants();

    /**
     * Gets all variants in the product including the master variant as first element in the list.
     *
     * @see #getMasterVariant()
     * @see #getVariants()
     * @return all variants
     */
    default List<ProductVariant> getAllVariants() {
        return ProductsPackage.getAllVariants(this);
    }

    /**
     * Finds a product variant by id.
     *
     * @param variantId the id of the variant to find
     * @return variant or null if no variant exists with {@code id}
     * @see #getVariantOrMaster(int)
     */
    @Nullable
    default ProductVariant getVariant(final int variantId) {
        final ProductVariant result;
        if (variantId == getMasterVariant().getId()) {
            result = getMasterVariant();
        } else {
            result = getVariants().stream().filter(v -> v.getId() == variantId).findFirst().orElse(null);
        }
        return result;
    }

    /**
     * Finds a product variant by id and returns the master variant if not variant with the id is present.
     * @param variantId the id of the variant to find
     * @return a variant
     * @see #getVariant(int)
     */
    default ProductVariant getVariantOrMaster(final int variantId) {
        return ProductsPackage.getVariantOrMaster(variantId, this);
    }

    SearchKeywords getSearchKeywords();

    @Nullable
    CategoryOrderHints getCategoryOrderHints();
}
