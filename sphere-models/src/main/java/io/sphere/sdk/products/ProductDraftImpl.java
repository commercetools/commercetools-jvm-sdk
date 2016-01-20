package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

class ProductDraftImpl extends Base implements ProductDraft {
    private final ResourceIdentifier<ProductType> productType;
    private final LocalizedString name;
    private final LocalizedString slug;
    @Nullable
    private final LocalizedString description;
    private final Set<Reference<Category>> categories;
    @Nullable
    private final LocalizedString metaTitle;
    @Nullable
    private final LocalizedString metaDescription;
    @Nullable
    private final LocalizedString metaKeywords;
    private final ProductVariantDraft masterVariant;
    private final List<ProductVariantDraft> variants;
    private final Reference<TaxCategory> taxCategory;
    private final SearchKeywords searchKeywords;
    @Nullable
    private final Reference<State> state;
    @Nullable
    private final CategoryOrderHints categoryOrderHints;

    @JsonCreator
    ProductDraftImpl(final ResourceIdentifier<ProductType> productType, final LocalizedString name, final LocalizedString slug,
                     @Nullable final LocalizedString description, final Set<Reference<Category>> categories,
                     @Nullable final LocalizedString metaTitle, @Nullable final LocalizedString metaDescription, @Nullable final LocalizedString metaKeywords, final ProductVariantDraft masterVariant,
                     final List<ProductVariantDraft> variants, final Reference<TaxCategory> taxCategory,
                     final SearchKeywords searchKeywords, @Nullable final Reference<State> state,
                     @Nullable final CategoryOrderHints categoryOrderHints) {
        this.name = name;
        this.productType = productType;
        this.slug = slug;
        this.description = description;
        this.categories = categories;
        this.taxCategory = taxCategory;
        this.searchKeywords = searchKeywords;
        this.state = state;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
        this.masterVariant = masterVariant;
        this.variants = variants;
        this.categoryOrderHints = categoryOrderHints;
    }

    @Override
    public ResourceIdentifier<ProductType> getProductType() {
        return productType;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public LocalizedString getSlug() {
        return slug;
    }

    @Override
    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Override
    public Set<Reference<Category>> getCategories() {
        return categories;
    }

    @Nullable
    @Override
    public LocalizedString getMetaTitle() {
        return metaTitle;
    }

    @Nullable
    @Override
    public LocalizedString getMetaDescription() {
        return metaDescription;
    }

    @Nullable
    @Override
    public LocalizedString getMetaKeywords() {
        return metaKeywords;
    }

    @Override
    public ProductVariantDraft getMasterVariant() {
        return masterVariant;
    }

    @Override
    public List<ProductVariantDraft> getVariants() {
        return variants;
    }

    @Nullable
    @Override
    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Override
    public SearchKeywords getSearchKeywords() {
        return searchKeywords;
    }

    @Override
    @Nullable
    public Reference<State> getState() {
        return state;
    }

    @Override
    @Nullable
    public CategoryOrderHints getCategoryOrderHints() {
        return categoryOrderHints;
    }
}
