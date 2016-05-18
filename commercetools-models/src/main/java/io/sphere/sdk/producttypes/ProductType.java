package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.products.Product;
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
public interface ProductType extends Resource<ProductType>, AttributeDefinitionContainer, WithKey {

    String getName();

    String getDescription();

    @Override
    List<AttributeDefinition> getAttributes();

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
