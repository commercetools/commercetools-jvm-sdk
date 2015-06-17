package io.sphere.sdk.categories;

import java.time.ZonedDateTime;
import java.util.Optional;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.List;

public abstract class CategoryWrapper implements Category {
    final Category delegate;

    public CategoryWrapper(Category delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public long getVersion() {
        return delegate.getVersion();
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return delegate.getCreatedAt();
    }

    @Override
    public ZonedDateTime getLastModifiedAt() {
        return delegate.getLastModifiedAt();
    }

    @Override
    public LocalizedStrings getName() {
        return delegate.getName();
    }

    @Override
    public LocalizedStrings getSlug() {
        return delegate.getSlug();
    }

    @Override
    public Optional<LocalizedStrings> getDescription() {
        return delegate.getDescription();
    }

    @Override
    public List<Reference<Category>> getAncestors() {
        return delegate.getAncestors();
    }

    @Override
    public Optional<Reference<Category>> getParent() {
        return delegate.getParent();
    }

    @Override
    public Optional<String> getOrderHint() {
        return delegate.getOrderHint();
    }


    @Override
    public Optional<String> getExternalId() {
        return delegate.getExternalId();
    }

    @Override
    public List<Category> getChildren() {
        return delegate.getChildren();
    }

    @Override
    public String toString() {
        return Category.toString(this);
    }

    @Override
    public Optional<LocalizedStrings> getMetaDescription() {
        return delegate.getMetaDescription();
    }

    @Override
    public Optional<LocalizedStrings> getMetaKeywords() {
        return delegate.getMetaKeywords();
    }

    @Override
    public Optional<LocalizedStrings> getMetaTitle() {
        return delegate.getMetaTitle();
    }
}
