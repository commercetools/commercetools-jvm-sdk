package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.search.SearchKeywords;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

class ProductDataImpl extends Base implements ProductData {
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
    private final CategoryOrderHints categoryOrderHints;

    @JsonCreator
    ProductDataImpl(final LocalizedString name, final Set<Reference<Category>> categories,
                    @Nullable final LocalizedString description, final LocalizedString slug,
                    @Nullable final LocalizedString metaTitle, @Nullable final LocalizedString metaDescription,
                    @Nullable final LocalizedString metaKeywords, final ProductVariant masterVariant,
                    final List<ProductVariant> variants, final SearchKeywords searchKeywords,
                    @Nullable final CategoryOrderHints categoryOrderHints) {
        this.name = name;
        this.categories = categories;
        this.description = description;
        this.slug = slug;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
        this.masterVariant = masterVariant;
        this.variants = variants;
        this.searchKeywords = searchKeywords;
        this.categoryOrderHints = categoryOrderHints;
    }

    public LocalizedString getName() {
        return name;
    }

    public Set<Reference<Category>> getCategories() {
        return categories;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    public LocalizedString getSlug() {
        return slug;
    }

    @Nullable
    public LocalizedString getMetaTitle() {
        return metaTitle;
    }

    @Nullable
    public LocalizedString getMetaDescription() {
        return metaDescription;
    }

    @Nullable
    public LocalizedString getMetaKeywords() {
        return metaKeywords;
    }

    public ProductVariant getMasterVariant() {
        return masterVariant;
    }

    public List<ProductVariant> getVariants() {
        return variants;
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

    void setProductId(final String id) {
        getAllVariants().stream()
                .filter(v -> v instanceof ProductVariantImpl)
                .forEach(variant -> ((ProductVariantImpl)variant).setProductId(id));
    }
}
