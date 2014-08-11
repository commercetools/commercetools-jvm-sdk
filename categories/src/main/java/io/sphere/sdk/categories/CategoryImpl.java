package io.sphere.sdk.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Collections;
import java.util.Optional;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;

import java.util.List;

@Immutable
class CategoryImpl extends DefaultModelImpl<Category> implements Category {
    private final LocalizedString name;
    private final LocalizedString slug;
    private final Optional<LocalizedString> description;
    private final List<Reference<Category>> ancestors;
    private final Optional<Reference<Category>> parent;
    private final Optional<String> orderHint;
    @JsonIgnore
    private final List<Category> children;
    private final List<Category> pathInTree;


    @JsonCreator
    CategoryImpl(final String id,
                         final long version,
                         final DateTime createdAt,
                         final DateTime lastModifiedAt,
                         final LocalizedString name,
                         final LocalizedString slug,
                         final Optional<LocalizedString> description,
                         final List<Reference<Category>> ancestors,
                         final Optional<Reference<Category>> parent,
                         final Optional<String> orderHint,
                         final List<Category> children,
                         final List<Category> pathInTree) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.ancestors = ancestors;
        this.parent = parent;
        this.orderHint = orderHint;
        this.children = children;
        this.pathInTree = pathInTree != null ? pathInTree : Collections.<Category>emptyList();
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public LocalizedString getSlug() {
        return slug;
    }

    @Override
    public Optional<LocalizedString> getDescription() {
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
    public String toString() {
        return Category.toString(this);
    }
}
