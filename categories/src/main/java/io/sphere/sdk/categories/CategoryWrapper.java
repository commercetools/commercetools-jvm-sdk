package io.sphere.sdk.categories;

import com.google.common.base.Optional;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import org.joda.time.DateTime;

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
    public DateTime getCreatedAt() {
        return delegate.getCreatedAt();
    }

    @Override
    public DateTime getLastModifiedAt() {
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

    @Override
    public Optional<LocalizedString> getDescription() {
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
    public List<Category> getChildren() {
        return delegate.getChildren();
    }

    @Override
    public List<Category> getPathInTree() {
        return delegate.getPathInTree();
    }

    @Override
    public String toString() {
        return Categories.toString(this);
    }
}
