package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface ProductDataLike extends WithLocalizedSlug, MetaAttributes {
    public LocalizedStrings getName();

    public List<Reference<Category>> getCategories();

    public Optional<LocalizedStrings> getDescription();

    public LocalizedStrings getSlug();

    public Optional<LocalizedStrings> getMetaTitle();

    public Optional<LocalizedStrings> getMetaDescription();

    public Optional<LocalizedStrings> getMetaKeywords();

    /**
     * Returns the master variant. Every product as 1 to n variants, so this is always present.
     *
     * @see #getAllVariants()
     * @see #getVariants()
     * @return the main variant in the product
     */
    public ProductVariant getMasterVariant();

    /**
     * Gets the variants which are not identical to the master variant. This list can be empty.
     *
     * @see #getAllVariants()
     * @see #getMasterVariant()
     *
     *
     * @return all variants except the one in {@link #getMasterVariant()}
     */
    public List<ProductVariant> getVariants();

    /**
     * Gets all variants in the product including the master variant as first element in the list.
     *
     * @see #getMasterVariant()
     * @see #getVariants()
     * @return all variants
     */
    default List<ProductVariant> getAllVariants() {
        final List<ProductVariant> nonMasterVariants = getVariants();
        final ArrayList<ProductVariant> result = new ArrayList<>(1 + nonMasterVariants.size());
        result.add(getMasterVariant());
        result.addAll(nonMasterVariants);
        return result;
    }
}
