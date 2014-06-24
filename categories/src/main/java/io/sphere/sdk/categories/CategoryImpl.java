package io.sphere.sdk.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.Ordering;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import net.jcip.annotations.Immutable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Immutable
public class CategoryImpl extends DefaultModelImpl implements Category {
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
    private CategoryImpl(@JsonProperty("id") final String id,
                         @JsonProperty("version") final long version,
                         @JsonProperty("createdAt") final DateTime createdAt,
                         @JsonProperty("lastModifiedAt") final DateTime lastModifiedAt,
                         @JsonProperty("name") final LocalizedString name,
                         @JsonProperty("slug") final LocalizedString slug,
                         @JsonProperty("description") final Optional<LocalizedString> description,
                         @JsonProperty("ancestors") final List<Reference<Category>> ancestors,
                         @JsonProperty("parent") final Optional<Reference<Category>> parent,
                         @JsonProperty("orderHint") final Optional<String> orderHint) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.ancestors = ancestors;
        this.parent = parent;
        this.orderHint = orderHint;
        this.children = Collections.emptyList();
        this.pathInTree = Collections.emptyList();
    }

    CategoryImpl(final CategoryBuilder builder) {
        super(builder.id, builder.version, builder.createdAt, builder.lastModifiedAt);
        name = builder.name;
        slug = builder.slug;
        description = builder.description;
        ancestors = builder.ancestors;
        parent = builder.parent;
        orderHint = builder.orderHint;
        children = builder.children;
        pathInTree = builder.pathInTree;
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
        return Categories.toString(this);
    }
}
