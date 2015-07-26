package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.search.SearchKeywords;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

class ProductDataImpl extends Base implements ProductData {
    private final LocalizedStrings name;

    private final Set<Reference<Category>> categories;

    @Nullable
    private final LocalizedStrings description;

    private final LocalizedStrings slug;
    @Nullable
    private final LocalizedStrings metaTitle;
    @Nullable
    private final LocalizedStrings metaDescription;
    @Nullable
    private final LocalizedStrings metaKeywords;

    private final ProductVariant masterVariant;

    private final List<ProductVariant> variants;

    private final SearchKeywords searchKeywords;

    @JsonCreator
    ProductDataImpl(final LocalizedStrings name, final Set<Reference<Category>> categories,
                    final LocalizedStrings description, final LocalizedStrings slug,
                    final LocalizedStrings metaTitle, final LocalizedStrings metaDescription,
                    final LocalizedStrings metaKeywords, final ProductVariant masterVariant,
                    final List<ProductVariant> variants, final SearchKeywords searchKeywords) {
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
    }

    public LocalizedStrings getName() {
        return name;
    }

    public Set<Reference<Category>> getCategories() {
        return categories;
    }

    @Nullable
    public LocalizedStrings getDescription() {
        return description;
    }

    public LocalizedStrings getSlug() {
        return slug;
    }

    @Nullable
    public LocalizedStrings getMetaTitle() {
        return metaTitle;
    }

    @Nullable
    public LocalizedStrings getMetaDescription() {
        return metaDescription;
    }

    @Nullable
    public LocalizedStrings getMetaKeywords() {
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

    void setProductId(final String id) {
        getAllVariants().stream()
                .filter(v -> v instanceof ProductVariantImpl)
                .forEach(variant -> ((ProductVariantImpl)variant).setProductId(id));
    }
}
