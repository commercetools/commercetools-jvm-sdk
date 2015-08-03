package io.sphere.sdk.categories;

import java.time.ZonedDateTime;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.List;

abstract class CategoryWrapper extends Base implements Category {
    final Category delegate;

    public CategoryWrapper(Category delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public Long getVersion() {
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

    @Nullable
    @Override
    public LocalizedStrings getDescription() {
        return delegate.getDescription();
    }

    @Override
    public List<Reference<Category>> getAncestors() {
        return delegate.getAncestors();
    }

    @Nullable
    @Override
    public Reference<Category> getParent() {
        return delegate.getParent();
    }

    @Nullable
    @Override
    public String getOrderHint() {
        return delegate.getOrderHint();
    }

    @Nullable
    @Override
    public String getExternalId() {
        return delegate.getExternalId();
    }

    @Override
    public String toString() {
        return Category.toString(this);
    }

    @Nullable
    @Override
    public LocalizedStrings getMetaDescription() {
        return delegate.getMetaDescription();
    }

    @Nullable
    @Override
    public LocalizedStrings getMetaKeywords() {
        return delegate.getMetaKeywords();
    }

    @Nullable
    @Override
    public LocalizedStrings getMetaTitle() {
        return delegate.getMetaTitle();
    }
}
