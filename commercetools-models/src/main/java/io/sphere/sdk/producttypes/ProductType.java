package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.attributes.AttributeConstraint;
import io.sphere.sdk.products.attributes.AttributeDefinition;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/** Describes common characteristics, most importantly common custom attributes, of many concrete products.

 @see io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand
 @see io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommand
 @see io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand
 @see io.sphere.sdk.producttypes.queries.ProductTypeQuery
 @see io.sphere.sdk.producttypes.queries.ProductTypeByIdGet
 @see io.sphere.sdk.producttypes.queries.ProductTypeByKeyGet
 @see Product#getProductType()
 */
@JsonDeserialize(as=ProductTypeImpl.class)
@ResourceValue
@HasQueryEndpoint(additionalContentsQueryInterface = "\n" +
        "    default ProductTypeQuery byName(String name) {\n" +
        "        return withPredicates(m -> m.name().is(name));\n" +
        "    }\n" +
        "\n" +
        "    default ProductTypeQuery byKey(String key) {\n" +
        "        return withPredicates(m -> m.key().is(key));\n" +
        "    }")
@ResourceInfo(pluralName = "product types", pathElement = "product-types")
@HasByIdGetEndpoint(javadocSummary = "Retrieves a product type by a known ID.", includeExamples = "io.sphere.sdk.producttypes.queries.ProductTypeByIdGetIntegrationTest#execution()")
@HasByKeyGetEndpoint(javadocSummary = "Retrieves a product type by a known key.", includeExamples = "io.sphere.sdk.producttypes.queries.ProductTypeByKeyGetIntegrationTest#execution()")
@HasCreateCommand(javadocSummary = "Command to create a {@link io.sphere.sdk.producttypes.ProductType} in the platform.\n" +
        "\n" +
        "\n" +
        "  <p>{@link io.sphere.sdk.producttypes.ProductType}s can be created in the backend by executing a {@link io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand}:</p>\n" +
        "\n" +
        "  {@include.example io.sphere.sdk.producttypes.commands.ProductTypeCreateCommandIntegrationTest#execution()}\n" +
        "\n" +
        "  {@include.example io.sphere.sdk.producttypes.Example#createDemo()}\n" +
        "\n" +
        "  {@include.example io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier}\n" +
        "\n" +
        "  <p>To create attribute definitions refer to {@link io.sphere.sdk.products.attributes.AttributeDefinition}.</p>")
@HasUpdateCommand(updateWith = "key")
@HasDeleteCommand(javadocSummary = "Deletes a product type.\n" +
        "\n" +
        " <p>Delete by ID:</p>\n" +
        " {@include.example io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommandIntegrationTest#execution()}\n" +
        " <p>Delete by key:</p>\n" +
        " {@include.example io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommandIntegrationTest#executionByKey()}", deleteWith = "key")
@HasQueryModel
public interface ProductType extends Resource<ProductType>, AttributeDefinitionContainer, WithKey {

    String getName();

    @IgnoreInQueryModel
    String getDescription();

    @Override
    @QueryModelHint(type = "AttributeDefinitionQueryModel<ProductType>", impl = "return new AttributeDefinitionQueryModelImpl<>(this, fieldName);")
    List<AttributeDefinition> getAttributes();

    @HasUpdateAction(value = "changeAttributeOrderByName", fields = {@PropertySpec(name="attributeNames", type = String[].class)})
    @HasUpdateAction(value = "changeAttributeConstraint",
            fields = {@PropertySpec(name = "attributeName", type = String.class),@PropertySpec(name = "newValue", type = AttributeConstraint.class)})
    @HasUpdateAction(value = "changeAttributeName",fields = {@PropertySpec(name = "attributeName",type = String.class),@PropertySpec(name = "newAttributeName",type = String.class)})
    @HasUpdateAction(value = "changeEnumKey",fields = {@PropertySpec(name = "attributeName",type = String.class),@PropertySpec(name = "key",type = String.class),@PropertySpec(name = "newKey",type = String.class)})
    @Nullable
    default AttributeDefinition getAttribute(final String attributeName) {
        return AttributeDefinitionContainer.super.getAttribute(attributeName);
    }

    @Override
    default Optional<AttributeDefinition> findAttribute(final String attributeName) {
        return AttributeDefinitionContainer.super.findAttribute(attributeName);
    }

    @Nullable
    String getKey();

    @Override
    default Reference<ProductType> toReference() {
        return reference(this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "product-type";
    }

    static String resourceTypeId() {
        return "product-type";
    }

    static Reference<ProductType> reference(final ProductType productType) {
        return Reference.of(referenceTypeId(), productType.getId(), productType);
    }

    static Reference<ProductType> reference(final String id) {
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
    static TypeReference<ProductType> typeReference() {
        return new TypeReference<ProductType>(){
            @Override
            public String toString() {
                return "TypeReference<ProductType>";
            }
        };
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
    static Reference<ProductType> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
