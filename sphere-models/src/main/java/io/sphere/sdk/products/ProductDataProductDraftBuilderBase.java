package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.*;
import io.sphere.sdk.search.SearchKeywords;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

abstract class ProductDataProductDraftBuilderBase<T extends ProductDataProductDraftBuilderBase<T>> extends Base implements WithLocalizedSlug, MetaAttributes {
    private final LocalizedStrings name;
    private final LocalizedStrings slug;
    private Optional<LocalizedStrings> description = Optional.empty();
    private Optional<LocalizedStrings> metaTitle = Optional.empty();
    private Optional<LocalizedStrings> metaDescription = Optional.empty();
    private Optional<LocalizedStrings> metaKeywords = Optional.empty();
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

    public T metaTitle(final Optional<LocalizedStrings> metaTitle) {
        this.metaTitle = metaTitle;
        return getThis();
    }

    public T metaTitle(final LocalizedStrings metaTitle) {
        return metaTitle(Optional.of(metaTitle));
    }

    public T metaDescription(final Optional<LocalizedStrings> metaDescription) {
        this.metaDescription = metaDescription;
        return getThis();
    }

    public T metaDescription(final LocalizedStrings metaDescription) {
        return metaDescription(Optional.of(metaDescription));
    }

    public T metaKeywords(final Optional<LocalizedStrings> metaKeywords) {
        this.metaKeywords = metaKeywords;
        return getThis();
    }

    public T metaKeywords(final LocalizedStrings metaKeywords) {
        return metaKeywords(Optional.of(metaKeywords));
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

    public Optional<LocalizedStrings> getMetaTitle() {
        return metaTitle;
    }

    public Optional<LocalizedStrings> getMetaDescription() {
        return metaDescription;
    }

    public Optional<LocalizedStrings> getMetaKeywords() {
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
