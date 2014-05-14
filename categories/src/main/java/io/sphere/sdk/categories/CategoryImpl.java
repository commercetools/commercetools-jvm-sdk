package io.sphere.sdk.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import io.sphere.sdk.common.models.LocalizedString;
import io.sphere.sdk.common.models.Reference;
import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;

import java.util.Collections;
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
    private final List<Reference<Category>> ancestors;
    private final Optional<Reference<Category>> parent;
    private final Optional<String> orderHint;
    @JsonIgnore
    private final List<Category> children;



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
        this.id = id;
        this.version = version;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.ancestors = ancestors;
        this.parent = parent;
        this.orderHint = orderHint;
        this.children = Collections.emptyList();
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryImpl)) return false;

        CategoryImpl category = (CategoryImpl) o;

        if (version != category.version) return false;
        if (!ancestors.equals(category.ancestors)) return false;
        if (!children.equals(category.children)) return false;
        if (!createdAt.equals(category.createdAt)) return false;
        if (!description.equals(category.description)) return false;
        if (!id.equals(category.id)) return false;
        if (!lastModifiedAt.equals(category.lastModifiedAt)) return false;
        if (!name.equals(category.name)) return false;
        if (!orderHint.equals(category.orderHint)) return false;
        if (!parent.equals(category.parent)) return false;
        if (!slug.equals(category.slug)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (int) (version ^ (version >>> 32));
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + lastModifiedAt.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + slug.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + ancestors.hashCode();
        result = 31 * result + parent.hashCode();
        result = 31 * result + orderHint.hashCode();
        result = 31 * result + children.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CategoryImpl{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", name=" + name +
                ", slug=" + slug +
                ", description=" + description +
                ", ancestors=" + ancestors +
                ", parent=" + parent +
                ", orderHint=" + orderHint +
                ", children=" + children +
                '}';
    }
}
