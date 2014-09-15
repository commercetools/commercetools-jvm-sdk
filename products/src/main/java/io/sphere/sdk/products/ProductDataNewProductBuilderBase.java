package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

abstract class ProductDataNewProductBuilderBase<T extends ProductDataNewProductBuilderBase<T>> extends Base implements WithLocalizedSlug, MetaAttributes {
    private LocalizedString name;
    private LocalizedString slug;
    private Optional<LocalizedString> description = Optional.empty();
    private Optional<LocalizedString> metaTitle = Optional.empty();
    private Optional<LocalizedString> metaDescription = Optional.empty();
    private Optional<LocalizedString> metaKeywords = Optional.empty();
    private List<Reference<Category>> categories = Collections.emptyList();

    protected ProductDataNewProductBuilderBase(final LocalizedString name, final LocalizedString slug) {
        this.name = name;
        this.slug = slug;
    }

    public T description(final Optional<LocalizedString> description) {
        this.description = description;
        return getThis();
    }

    public T description(final LocalizedString description) {
        return description(Optional.of(description));
    }

    public T metaTitle(final Optional<LocalizedString> metaTitle) {
        this.metaTitle = metaTitle;
        return getThis();
    }

    public T metaTitle(final LocalizedString metaTitle) {
        return metaTitle(Optional.of(metaTitle));
    }

    public T metaDescription(final Optional<LocalizedString> metaDescription) {
        this.metaDescription = metaDescription;
        return getThis();
    }

    public T metaDescription(final LocalizedString metaDescription) {
        return metaDescription(Optional.of(metaDescription));
    }

    public T metaKeywords(final Optional<LocalizedString> metaKeywords) {
        this.metaKeywords = metaKeywords;
        return getThis();
    }

    public T metaKeywords(final LocalizedString metaKeywords) {
        return metaKeywords(Optional.of(metaKeywords));
    }

    public T categories(final List<Reference<Category>> categories) {
        this.categories = categories;
        return getThis();
    }

    public LocalizedString getName() {
        return name;
    }

    public LocalizedString getSlug() {
        return slug;
    }

    public Optional<LocalizedString> getDescription() {
        return description;
    }

    public Optional<LocalizedString> getMetaTitle() {
        return metaTitle;
    }

    public Optional<LocalizedString> getMetaDescription() {
        return metaDescription;
    }

    public Optional<LocalizedString> getMetaKeywords() {
        return metaKeywords;
    }

    public List<Reference<Category>> getCategories() {
        return categories;
    }

    protected abstract T getThis();
}
