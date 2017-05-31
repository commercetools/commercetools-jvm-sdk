package io.sphere.sdk.categories;

import java.time.ZonedDateTime;

import io.sphere.sdk.models.Asset;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

abstract class CategoryWrapper extends Base implements Category {
    final Category delegate;

    public CategoryWrapper(Category delegate) {
        this.delegate = delegate;
    }

    @Nullable
    @Override
    public String getKey() {
        return delegate.getKey();
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
    public LocalizedString getName() {
        return delegate.getName();
    }

    @Override
    public LocalizedString getSlug() {
        return delegate.getSlug();
    }

    @Nullable
    @Override
    public LocalizedString getDescription() {
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
    public LocalizedString getMetaDescription() {
        return delegate.getMetaDescription();
    }

    @Nullable
    @Override
    public LocalizedString getMetaKeywords() {
        return delegate.getMetaKeywords();
    }

    @Nullable
    @Override
    public LocalizedString getMetaTitle() {
        return delegate.getMetaTitle();
    }

    @Nullable
    @Override
    public CustomFields getCustom() {
        return delegate.getCustom();
    }

    @Nonnull
    @Override
    public List<Asset> getAssets() {
        return delegate.getAssets();
    }
}
