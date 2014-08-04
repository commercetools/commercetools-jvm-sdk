package io.sphere.sdk.categories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import java.util.Optional;
import com.google.common.collect.Iterables;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;

import java.util.List;

@JsonDeserialize(as=CategoryImpl.class)
public interface Category extends DefaultModel<Category>, WithLocalizedSlug {

    LocalizedString getName();

    LocalizedString getSlug();

    Optional<LocalizedString> getDescription();

    List<Reference<Category>> getAncestors();

    Optional<Reference<Category>> getParent();

    Optional<String> getOrderHint();

    List<Category> getChildren();

    /**
     * The path to this category in the category tree, starting with the root and ending with this category.
     */
    List<Category> getPathInTree();

    @Override
    default Reference<Category> toReference() {
        return reference(this);
    }

    public static String typeId(){
        return "category";
    }

    public static Reference<Category> reference(final Category category) {
        return new Reference<>(typeId(), category.getId(), Optional.ofNullable(category));
    }

    public static Optional<Reference<Category>> reference(final Optional<Category> category) {
        return category.map(Category::reference);
    }

    public static Reference<Category> reference(final String id) {
        return Reference.of(typeId(), id);
    }

    public static String toString(final Category category) {
        return Objects.toStringHelper(category.getClass()).
                add("id", category.getId()).
                add("version", category.getVersion()).
                add("createdAt", category.getCreatedAt()).
                add("lastModifiedAt", category.getLastModifiedAt()).
                add("name", category.getName()).
                add("slug", category.getSlug()).
                add("description", category.getDescription()).
                add("ancestors", Joiner.on(", ").join(category.getAncestors())).
                add("parent", category.getParent()).
                add("orderHint", category.getOrderHint()).
                add("children", category.getChildren()).
                add("pathInTree", Joiner.on(", ").join(Iterables.transform(category.getPathInTree(), Category::getId))).toString();
    }
}
