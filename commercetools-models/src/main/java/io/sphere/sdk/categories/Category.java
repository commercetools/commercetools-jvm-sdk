package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.ProductData;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

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
@ResourceValue
@HasQueryEndpoint(additionalContentsQueryInterface = {"    default CategoryQuery bySlug(final Locale locale, final String slug) {\n" +
        "        return withPredicates(m -> m.slug().lang(locale).is(slug));\n" +
        "    }\n" +
        "\n" +
        "    default CategoryQuery byName(final Locale locale, final String name) {\n" +
        "        return withPredicates(m -> m.name().lang(locale).is(name));\n" +
        "    }\n" +
        "\n" +
        "    default CategoryQuery byId(final String id) {\n" +
        "        return withPredicates(m -> m.id().is(id));\n" +
        "    }\n" +
        "\n" +
        "    default CategoryQuery byIsRoot() {\n" +
        "        return withPredicates(m -> m.parent().isNotPresent());\n" +
        "    }\n" +
        "\n" +
        "    default CategoryQuery byExternalId(final String externalId) {\n" +
        "        return withPredicates(m -> m.externalId().is(externalId));\n" +
        "    }"})
@ResourceInfo(pluralName = "categories", pathElement = "categories")
@HasByIdGetEndpoint(javadocSummary = "Retrieves a category by a known ID.", includeExamples = "io.sphere.sdk.categories.queries.CategoryByIdGetIntegrationTest#execution()")
@HasByKeyGetEndpoint(javadocSummary = "Retrieves a category by a known Key.", includeExamples = "io.sphere.sdk.categories.queries.CategoryByKeydGetIntegrationTest#execution()")
@HasCreateCommand(includeExamples = "io.sphere.sdk.categories.commands.CategoryCreateCommandIntegrationTest#execution()")
@HasUpdateCommand(javadocSummary = "Updates a category.")
@HasDeleteCommand(javadocSummary = "Deletes a category.", includeExamples = "io.sphere.sdk.categories.commands.CategoryDeleteCommandIntegrationTest#execution()", deleteWith = "key")
@HasQueryModel(baseInterfaces = {"io.sphere.sdk.queries.QueryModel<io.sphere.sdk.categories.Category>"})
public interface Category extends Resource<Category>, WithLocalizedSlug, MetaAttributes, Custom, WithKey {


    /**
     * User-specific unique identifier for the Category.
     *
     * @return the user defined key
     */
    @Override
    @Nullable
    String getKey();

    /**
     * Name of this category.
     *
     * @see io.sphere.sdk.categories.commands.updateactions.ChangeName
     *
     * @return name
     */
    LocalizedString getName();

    /**
     * Human-readable identifier usually used as deep-link URL part.
     *
     * @see io.sphere.sdk.categories.commands.updateactions.ChangeSlug
     *
     * @return slug
     */
    LocalizedString getSlug();

    /**
     * Description for this category.
     *
     * @see io.sphere.sdk.categories.commands.updateactions.SetDescription
     *
     * @return description or null
     */
    @Nullable
    @IgnoreInQueryModel
    LocalizedString getDescription();

    @IgnoreInQueryModel
    List<Reference<Category>> getAncestors();

    /**
     * Reference to the parent category.
     *
     * @see io.sphere.sdk.categories.commands.updateactions.ChangeParent
     *
     * @return parent reference or null
     */
    @Nullable
    Reference<Category> getParent();

    /**
     * An attribute as base for a custom category order in one level.
     *
     * @see io.sphere.sdk.categories.commands.updateactions.ChangeOrderHint
     *
     * @return order hint or null
     */
    @Nullable
    String getOrderHint();

    /**
     * ID which can be used as additional identifier for external Systems like CRM or ERP.
     *
     * @see io.sphere.sdk.categories.commands.updateactions.SetExternalId
     *
     * @return external ID or null
     */
    @Nullable
    String getExternalId();

    /**
     * SEO meta title.
     *
     * @see io.sphere.sdk.categories.commands.updateactions.SetMetaTitle
     *
     * @return SEO meta title.
     */
    @Nullable
    @Override
    @IgnoreInQueryModel
    LocalizedString getMetaTitle();

    /**
     * SEO meta description.
     *
     * @see io.sphere.sdk.categories.commands.updateactions.SetMetaDescription
     *
     * @return SEO meta description.
     */
    @Nullable
    @Override
    @IgnoreInQueryModel
    LocalizedString getMetaDescription();

    /**
     * SEO meta keywords.
     *
     * @see io.sphere.sdk.categories.commands.updateactions.SetMetaKeywords
     *
     * @return SEO meta keywords.
     */
    @Nullable
    @Override
    @IgnoreInQueryModel
    LocalizedString getMetaKeywords();

    /**
     * Custom fields.
     *
     * @see Custom
     * @see io.sphere.sdk.categories.commands.updateactions.SetCustomField
     * @see io.sphere.sdk.categories.commands.updateactions.SetCustomType
     *
     * @return custom fields
     */
    @Nullable
    CustomFields getCustom();

    @Nonnull
    @IgnoreInQueryModel
    @HasUpdateAction(value = "setAssetKey", fields = {@PropertySpec(name = "assetId", type = String.class), @PropertySpec(name = "assetKey", type = String.class, isOptional = true)})
    @HasUpdateAction(value = "addAsset", fields = {@PropertySpec(name = "asset", type = AssetDraft.class), @PropertySpec(name = "position", type = Integer.class)},
            factoryMethods = {@FactoryMethod(parameterNames = {"asset"})})
    @HasUpdateAction(value = "removeAsset", fields = {@PropertySpec(name = "assetId", type = String.class), @PropertySpec(name = "assetKey", type = String.class)},
            factoryMethods = {@FactoryMethod(parameterNames = {"assetId"}), @FactoryMethod(methodName = "ofKey", parameterNames = {"assetKey"})}, generateDefaultFactory = false)
    @HasUpdateAction(value = "changeAssetName", fields = {@PropertySpec(name = "assetId", type = String.class), @PropertySpec(name = "assetKey", type = String.class), @PropertySpec(name = "name", type = LocalizedString.class)},
            factoryMethods = {@FactoryMethod(parameterNames = {"assetId", "name"}), @FactoryMethod(methodName = "ofKey", parameterNames = {"assetKey", "name"})}, generateDefaultFactory = false)
    @HasUpdateAction(value = "setAssetDescription", fields = {@PropertySpec(name = "assetId", type = String.class), @PropertySpec(name = "assetKey", type = String.class), @PropertySpec(name = "description", type = LocalizedString.class)},
            factoryMethods = {@FactoryMethod(parameterNames = {"assetId", "description"}), @FactoryMethod(methodName = "ofKey", parameterNames = {"assetKey", "description"})}, generateDefaultFactory = false)
    List<Asset> getAssets();

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
        return new ReflectionToStringBuilder(category, SdkDefaults.TO_STRING_STYLE)
                .build();
    }

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<Category> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
