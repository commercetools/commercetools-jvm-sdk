package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.*;
import io.sphere.sdk.search.SearchKeywords;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

abstract class ProductDataProductDraftBuilderBase<T extends ProductDataProductDraftBuilderBase<T>> extends Base implements WithLocalizedSlug, MetaAttributes {
    private final LocalizedStrings name;
    private final LocalizedStrings slug;
    private Optional<LocalizedStrings> description = Optional.empty();
    private LocalizedStrings metaTitle;
    private LocalizedStrings metaDescription;
    private LocalizedStrings metaKeywords;
    private Set<Reference<Category>> categories = Collections.emptySet();
    private SearchKeywords searchKeywords = SearchKeywords.of();

    protected ProductDataProductDraftBuilderBase(final LocalizedStrings name, final LocalizedStrings slug) {
        this.name = name;
        this.slug = slug;
    }

    public T description(final Optional<LocalizedStrings> description) {
        this.description = description;
        return getThis();
    }

    public T description(final LocalizedStrings description) {
        return description(Optional.of(description));
    }

    public T metaTitle(@Nullable final LocalizedStrings metaTitle) {
        this.metaTitle = metaTitle;
        return getThis();
    }

    public T metaDescription(@Nullable final LocalizedStrings metaDescription) {
        this.metaDescription = metaDescription;
        return getThis();
    }

    public T metaKeywords(@Nullable final LocalizedStrings metaKeywords) {
        this.metaKeywords = metaKeywords;
        return getThis();
    }



    public T categories(final Set<Reference<Category>> categories) {
        this.categories = categories;
        return getThis();
    }

    public T searchKeywords(final SearchKeywords searchKeywords) {
        this.searchKeywords = searchKeywords;
        return getThis();
    }

    public LocalizedStrings getName() {
        return name;
    }

    public LocalizedStrings getSlug() {
        return slug;
    }

    public Optional<LocalizedStrings> getDescription() {
        return description;
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

    public Set<Reference<Category>> getCategories() {
        return categories;
    }

    public SearchKeywords getSearchKeywords() {
        return searchKeywords;
    }

    protected abstract T getThis();
}
