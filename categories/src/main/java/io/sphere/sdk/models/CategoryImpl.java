package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Optional;
import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;

import java.util.List;

@Immutable
public class CategoryImpl implements Category {
    private final String id;
    private final long version;
    private final DateTime createdAt;
    private final DateTime lastModifiedAt;
    private final LocalizedString name;
    private final LocalizedString slug;
    private final Optional<LocalizedString> description;
    private final List<Reference<CategoryImpl>> ancestors;
    private final Optional<Reference<CategoryImpl>> parent;
    private final Optional<String> orderHint;
    @JsonIgnore
    private final List<CategoryImpl> children;

    CategoryImpl(final CategoryBuilder builder) {
        id = builder.id;
        version = builder.version;
        createdAt = builder.createdAt;
        lastModifiedAt = builder.lastModifiedAt;
        name = builder.name;
        slug = builder.slug;
        description = builder.description;
        ancestors = builder.ancestors;
        parent = builder.parent;
        orderHint = builder.orderHint;
        children = builder.children;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public DateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public DateTime getLastModifiedAt() {
        return lastModifiedAt;
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
    public List<Reference<CategoryImpl>> getAncestors() {
        return ancestors;
    }

    @Override
    public Optional<Reference<CategoryImpl>> getParent() {
        return parent;
    }

    @Override
    public Optional<String> getOrderHint() {
        return orderHint;
    }

    @Override
    public List<CategoryImpl> getChildren() {
        return children;
    }
}
