package io.sphere.sdk.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.List;

class CategoryImpl extends DefaultModelImpl<Category> implements Category {
    private final LocalizedStrings name;
    private final LocalizedStrings slug;
    private final Optional<LocalizedStrings> description;
    private final List<Reference<Category>> ancestors;
    private final Optional<Reference<Category>> parent;
    private final Optional<String> orderHint;
    private final Optional<String> externalId;
    @JsonIgnore
    private final List<Category> children;
    private final List<Category> pathInTree;


    @JsonCreator
    CategoryImpl(final String id,
                 final long version,
                 final Instant createdAt,
                 final Instant lastModifiedAt,
                 final LocalizedStrings name,
                 final LocalizedStrings slug,
                 final Optional<LocalizedStrings> description,
                 final List<Reference<Category>> ancestors,
                 final Optional<Reference<Category>> parent,
                 final Optional<String> orderHint, final Optional<String> externalId,
                 final List<Category> children,
                 final List<Category> pathInTree) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.ancestors = ancestors;
        this.parent = parent;
        this.orderHint = orderHint;
        this.externalId = externalId;
        this.children = children;
        this.pathInTree = pathInTree != null ? pathInTree : Collections.<Category>emptyList();
    }

    @Override
    public LocalizedStrings getName() {
        return name;
    }

    @Override
    public LocalizedStrings getSlug() {
        return slug;
    }

    @Override
    public Optional<LocalizedStrings> getDescription() {
        return description;
    }

    @Override
    public List<Reference<Category>> getAncestors() {
        return ancestors;
    }

    @Override
    public Optional<Reference<Category>> getParent() {
        return parent;
    }

    @Override
    public Optional<String> getOrderHint() {
        return orderHint;
    }

    @Override
    public List<Category> getChildren() {
        return children;
    }

    @Override
    public List<Category> getPathInTree() {
        return pathInTree;
    }

    @Override
    public Optional<String> getExternalId() {
        return externalId;
    }

    @Override
    public String toString() {
        return Category.toString(this);
    }
}
