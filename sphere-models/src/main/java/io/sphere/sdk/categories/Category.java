package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.sphere.sdk.models.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.utils.ListUtils.join;

/**
 * Categories are used to organize products in a hierarchical structure.
 *
 * <p id="operations">Operations:</p>
 * <ul>
 *     <li>Create a category with {@link io.sphere.sdk.categories.commands.CategoryCreateCommand}.</li>
 *     <li>Update a category with {@link io.sphere.sdk.categories.commands.CategoryUpdateCommand}.</li>
 *     <li>Delete a category with {@link io.sphere.sdk.categories.commands.CategoryDeleteCommand}.</li>
 * </ul>
 */
@JsonDeserialize(as=CategoryImpl.class)
public interface Category extends DefaultModel<Category>, WithLocalizedSlug, MetaAttributes {

    LocalizedStrings getName();

    LocalizedStrings getSlug();

    @Nullable
    LocalizedStrings getDescription();

    List<Reference<Category>> getAncestors();

    @Nullable
    Reference<Category> getParent();

    @Nullable
    String getOrderHint();

    @Nullable
    String getExternalId();

    @Nullable
    @Override
    LocalizedStrings getMetaTitle();

    @Nullable
    @Override
    LocalizedStrings getMetaDescription();

    @Nullable
    @Override
    LocalizedStrings getMetaKeywords();

    @Override
    default Reference<Category> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

    static String typeId(){
        return "category";
    }

    static Reference<Category> reference(final String id) {
        return Reference.of(typeId(), id);
    }


    static TypeReference<Category> typeReference() {
        return new TypeReference<Category>() {
            @Override
            public String toString() {
                return "TypeReference<Category>";
            }
        };
    }

    static String toString(final Category category) {
        return new ToStringBuilder(category, SdkDefaults.TO_STRING_STYLE)
                .append("id", category.getId())
                .append("version", category.getVersion())
                .append("createdAt", category.getCreatedAt())
                .append("lastModifiedAt", category.getLastModifiedAt())
                .append("name", category.getName())
                .append("slug", category.getSlug())
                .append("description", category.getDescription())
                .append("ancestors", join(category.getAncestors()))
                .append("parent", category.getParent())
                .append("orderHint", category.getOrderHint())
                .toString();
    }
}
