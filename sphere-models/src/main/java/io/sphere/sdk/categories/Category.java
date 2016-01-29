package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.sphere.sdk.models.*;
import io.sphere.sdk.products.ProductData;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.utils.SphereInternalUtils.join;

/**
 * Categories are used to organize products in a hierarchical structure.
 *
 *  <p>A category can have {@link io.sphere.sdk.types.Custom custom fields}.</p>
 *
 * <p>Consult the documentation for <a href="{@docRoot}/io/sphere/sdk/meta/CategoryDocumentation.html">categories</a> for more information.</p>
 * @see io.sphere.sdk.categories.commands.CategoryCreateCommand
 * @see io.sphere.sdk.categories.commands.CategoryUpdateCommand
 * @see io.sphere.sdk.categories.commands.CategoryDeleteCommand
 * @see io.sphere.sdk.categories.queries.CategoryQuery
 * @see io.sphere.sdk.categories.queries.CategoryByIdGet
 * @see CategoryTree
 * @see ProductData#getCategories()
 * @see ProductData#getCategoryOrderHints()
 * @see ProductProjection#getCategories()
 * @see ProductProjection#getCategoryOrderHints()
 * @see io.sphere.sdk.products.commands.updateactions.AddToCategory
 * @see io.sphere.sdk.products.commands.updateactions.RemoveFromCategory
 */
@JsonDeserialize(as=CategoryImpl.class)
public interface Category extends Resource<Category>, WithLocalizedSlug, MetaAttributes, Custom {

    LocalizedString getName();

    LocalizedString getSlug();

    @Nullable
    LocalizedString getDescription();

    List<Reference<Category>> getAncestors();

    @Nullable
    Reference<Category> getParent();

    @Nullable
    String getOrderHint();

    @Nullable
    String getExternalId();

    @Nullable
    @Override
    LocalizedString getMetaTitle();

    @Nullable
    @Override
    LocalizedString getMetaDescription();

    @Nullable
    @Override
    LocalizedString getMetaKeywords();

    @Nullable
    CustomFields getCustom();

    @Override
    default Reference<Category> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "category";
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "category";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId(){
        return "category";
    }

    static Reference<Category> reference(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
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

    static Reference<Category> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
