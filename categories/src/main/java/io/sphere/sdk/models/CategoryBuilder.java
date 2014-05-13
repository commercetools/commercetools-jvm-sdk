package io.sphere.sdk.models;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

public class CategoryBuilder {
    String id;
    long version = 1;
    DateTime createdAt = new DateTime();
    DateTime lastModifiedAt = new DateTime();
    LocalizedString name;
    LocalizedString slug;
    Optional<LocalizedString> description = Optional.absent();
    List<Reference<CategoryImpl>> ancestors = Collections.emptyList();
    Optional<Reference<CategoryImpl>> parent = Optional.absent();
    Optional<String> orderHint = Optional.absent();
    List<CategoryImpl> children = Collections.emptyList();

    private CategoryBuilder(final String id, final LocalizedString name, final LocalizedString slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public CategoryBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public CategoryBuilder version(final long version) {
        this.version = version;
        return this;
    }

    public CategoryBuilder createdAt(final DateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CategoryBuilder lastModifiedAt(final DateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
        return this;
    }

    public CategoryBuilder name(final LocalizedString name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder slug(final LocalizedString slug) {
        this.slug = slug;
        return this;
    }

    public CategoryBuilder description(final Optional<LocalizedString> description) {
        this.description = description;
        return this;
    }

    public CategoryBuilder description(final LocalizedString description) {
        this.description = Optional.fromNullable(description);
        return this;
    }

    public CategoryBuilder ancestors(final List<Reference<CategoryImpl>> ancestors) {
        this.ancestors = ancestors;
        return this;
    }

    public CategoryBuilder parent(final Optional<Reference<CategoryImpl>> parent) {
        this.parent = parent;
        return this;
    }

    public CategoryBuilder parent(final Reference<CategoryImpl> parent) {
        this.parent = Optional.fromNullable(parent);
        return this;
    }

    public CategoryBuilder orderHint(final String orderHint) {
        this.orderHint = Optional.fromNullable(orderHint);
        return this;
    }

    public CategoryBuilder children(final List<CategoryImpl> children) {
        this.children = children;
        return this;
    }

    public CategoryImpl build() {
        return new CategoryImpl(this);
    }
}
