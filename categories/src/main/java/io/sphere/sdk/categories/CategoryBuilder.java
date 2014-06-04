package io.sphere.sdk.categories;

import com.google.common.base.Optional;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
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
    List<Reference<Category>> ancestors = Collections.emptyList();
    Optional<Reference<Category>> parent = Optional.absent();
    Optional<String> orderHint = Optional.absent();
    List<Category> children = Collections.emptyList();
    List<Category> pathInTree;

    public static CategoryBuilder of(final String id, final LocalizedString name, final LocalizedString slug) {
        return new CategoryBuilder(id, name, slug);
    }

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

    public CategoryBuilder ancestors(final List<Reference<Category>> ancestors) {
        this.ancestors = ancestors;
        return this;
    }

    public CategoryBuilder parent(final Optional<Reference<Category>> parent) {
        this.parent = parent;
        return this;
    }

    public CategoryBuilder parent(final Reference<Category> parent) {
        this.parent = Optional.fromNullable(parent);
        return this;
    }

    public CategoryBuilder orderHint(final String orderHint) {
        this.orderHint = Optional.fromNullable(orderHint);
        return this;
    }

    public CategoryBuilder children(final List<Category> children) {
        this.children = children;
        return this;
    }

    public CategoryBuilder pathInTree(final List<Category> pathInTree) {
        this.pathInTree = pathInTree;
        return this;
    }

    public Category build() {
        return new CategoryImpl(this);
    }
}
