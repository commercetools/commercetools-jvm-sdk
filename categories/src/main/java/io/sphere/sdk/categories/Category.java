package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Objects;
import java.util.Optional;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;

import java.util.List;

import static io.sphere.sdk.utils.ListUtils.join;
import static java.util.stream.Collectors.toList;

/**
 * Categories are used to organize products in a hierarchical structure.
 *
 * <ol>
 *     <li>{@link io.sphere.sdk.categories.commands.CategoryCreateCommand create a category in SPHERE.IO}</li>
 *     <li>{@link io.sphere.sdk.categories.CategoryBuilder create a category for unit tests}</li>
 *     <li>{@link io.sphere.sdk.categories.commands.CategoryDeleteByIdCommand delete a category in SPHERE.IO}</li>
 * </ol>
 */
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
        return new Reference<>(typeId(), getId(), Optional.of(this));
    }

    public static String typeId(){
        return "category";
    }

    public static Reference<Category> reference(final String id) {
        return Reference.of(typeId(), id);
    }


    public static TypeReference<Category> typeReference() {
        return new TypeReference<Category>() {
            @Override
            public String toString() {
                return "TypeReference<Category>";
            }
        };
    }

    public static String toString(final Category category) {
        final List<String> pathInTreeIds = category.getPathInTree().stream().map(Category::getId).collect(toList());
        return Objects.toStringHelper(category.getClass()).
                add("id", category.getId()).
                add("version", category.getVersion()).
                add("createdAt", category.getCreatedAt()).
                add("lastModifiedAt", category.getLastModifiedAt()).
                add("name", category.getName()).
                add("slug", category.getSlug()).
                add("description", category.getDescription()).
                add("ancestors", join(category.getAncestors())).
                add("parent", category.getParent()).
                add("orderHint", category.getOrderHint()).
                add("children", category.getChildren()).
                add("pathInTree", join(pathInTreeIds)).toString();
    }
}
