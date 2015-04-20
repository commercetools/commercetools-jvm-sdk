package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;

import io.sphere.sdk.models.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static io.sphere.sdk.utils.ListUtils.join;
import static java.util.stream.Collectors.toList;

/**
 * Categories are used to organize products in a hierarchical structure.
 *
 * <p id="operations">Operations:</p>
 * <ul>
 *     <li>Create a category with {@link io.sphere.sdk.categories.commands.CategoryCreateCommand}.</li>
 *     <li>Create a category test double with {@link io.sphere.sdk.categories.CategoryBuilder}.</li>
 *     <li>Update a category with {@link io.sphere.sdk.categories.commands.CategoryUpdateCommand}.</li>
 *     <li>Delete a category with {@link io.sphere.sdk.categories.commands.CategoryDeleteCommand}.</li>
 * </ul>
 */
@JsonDeserialize(as=CategoryImpl.class)
public interface Category extends DefaultModel<Category>, WithLocalizedSlug {

    LocalizedStrings getName();

    LocalizedStrings getSlug();

    Optional<LocalizedStrings> getDescription();

    List<Reference<Category>> getAncestors();

    Optional<Reference<Category>> getParent();

    Optional<String> getOrderHint();

    Optional<String> getExternalId();

    List<Category> getChildren();

    List<Category> getPathInTree();

    @Override
    default Reference<Category> toReference() {
        return Reference.of(typeId(), getId(), this);
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
        return new ToStringBuilder(category, SdkDefaults.TO_STRING_STYLE).
                append("id", category.getId()).
                append("version", category.getVersion()).
                append("createdAt", category.getCreatedAt()).
                append("lastModifiedAt", category.getLastModifiedAt()).
                append("name", category.getName()).
                append("slug", category.getSlug()).
                append("description", category.getDescription()).
                append("ancestors", join(category.getAncestors())).
                append("parent", category.getParent()).
                append("orderHint", category.getOrderHint()).
                append("children", category.getChildren()).
                append("pathInTree", join(pathInTreeIds)).toString();
    }
}
