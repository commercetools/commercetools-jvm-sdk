package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.*;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Optional;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

abstract class ProductDataProductDraftBuilderBase<T extends ProductDataProductDraftBuilderBase<T>> extends Base implements WithLocalizedSlug, MetaAttributes {
    private LocalizedString name;
    private LocalizedString slug;
    private LocalizedString description;
    private LocalizedString metaTitle;
    private LocalizedString metaDescription;
    private LocalizedString metaKeywords;
    private Set<Reference<Category>> categories = Collections.emptySet();
    private SearchKeywords searchKeywords = SearchKeywords.of();
    private Reference<TaxCategory> taxCategory;
    @Nullable
    private Reference<State> state;
    @Nullable
    private CategoryOrderHints categoryOrderHints;

    protected ProductDataProductDraftBuilderBase(final LocalizedString name, final LocalizedString slug) {
        this.name = name;
        this.slug = slug;
    }

    public T slug(final LocalizedString slug) {
        this.slug = slug;
        return getThis();
    }

    public T name(final LocalizedString name) {
        this.name = name;
        return getThis();
    }

    public T description(@Nullable final LocalizedString description) {
        this.description = description;
        return getThis();
    }

    public T metaTitle(@Nullable final LocalizedString metaTitle) {
        this.metaTitle = metaTitle;
        return getThis();
    }

    public T metaDescription(@Nullable final LocalizedString metaDescription) {
        this.metaDescription = metaDescription;
        return getThis();
    }

    public T metaKeywords(@Nullable final LocalizedString metaKeywords) {
        this.metaKeywords = metaKeywords;
        return getThis();
    }

    public T categories(final Set<Reference<Category>> categories) {
        this.categories = categories;
        return getThis();
    }

    public T categories(final List<Reference<Category>> categories) {
        return categories(new LinkedHashSet<>(categories));
    }

    public T searchKeywords(final SearchKeywords searchKeywords) {
        this.searchKeywords = searchKeywords;
        return getThis();
    }

    public T categoryOrderHints(@Nullable final CategoryOrderHints categoryOrderHints) {
        this.categoryOrderHints = categoryOrderHints;
        return getThis();
    }

    public LocalizedString getName() {
        return name;
    }

    public LocalizedString getSlug() {
        return slug;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
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

    public Set<Reference<Category>> getCategories() {
        return categories;
    }

    public SearchKeywords getSearchKeywords() {
        return searchKeywords;
    }

    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    public T taxCategory(final Referenceable<TaxCategory> taxCategory) {
        this.taxCategory = taxCategory.toReference();
        return getThis();
    }

    public T state(@Nullable final Referenceable<State> state) {
        this.state = Optional.ofNullable(state).map(Referenceable::toReference).orElse(null);
        return getThis();
    }

    @Nullable
    public Reference<State> getState() {
        return state;
    }

    @Nullable
    public CategoryOrderHints getCategoryOrderHints() {
        return categoryOrderHints;
    }

    protected abstract T getThis();
}
