package io.sphere.sdk.categories;

import java.util.Optional;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import net.jcip.annotations.Immutable;

@Immutable
public class NewCategory {
    private final LocalizedString name;
    private final LocalizedString slug;
    private final Optional<LocalizedString> description;
    private final Optional<Reference<Category>> parent;
    private final Optional<String> orderHint;

    NewCategory(final LocalizedString name, final LocalizedString slug,
                final Optional<LocalizedString> description, final Optional<Reference<Category>> parent,
                final Optional<String> orderHint) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parent = parent;
        this.orderHint = orderHint;
    }

    public LocalizedString getName() {
        return name;
    }

    public LocalizedString getSlug() {
        return slug;
    }

    public Optional<LocalizedString> getDescription() {
        return description;
    }

    public Optional<Reference<Category>> getParent() {
        return parent;
    }

    public Optional<String> getOrderHint() {
        return orderHint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewCategory)) return false;

        NewCategory that = (NewCategory) o;

        if (!description.equals(that.description)) return false;
        if (!name.equals(that.name)) return false;
        if (!orderHint.equals(that.orderHint)) return false;
        if (!parent.equals(that.parent)) return false;
        if (!slug.equals(that.slug)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + slug.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + parent.hashCode();
        result = 31 * result + orderHint.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NewCategory{" +
                "name=" + name +
                ", slug=" + slug +
                ", description=" + description +
                ", parent=" + parent +
                ", orderHint=" + orderHint +
                '}';
    }
}
