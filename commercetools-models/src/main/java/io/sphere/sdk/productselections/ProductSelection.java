package io.sphere.sdk.productselections;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.*;
import io.sphere.sdk.types.Custom;

import javax.annotation.Nullable;

@JsonDeserialize(as= ProductSelectionImpl.class)
@ResourceValue
@HasQueryEndpoint()
@ResourceInfo(pluralName = "product selections", pathElement = "product-selections")
@HasByIdGetEndpoint(javadocSummary = "Gets a product selection by ID.", includeExamples = "io.sphere.sdk.productselections.queries.ProductSelectionByIdGetIntegrationTest#fetchById()")
@HasByKeyGetEndpoint (javadocSummary = "Gets a product selection by Key.", includeExamples = "io.sphere.sdk.productselections.queries.ProductSelectionByKeyGetIntegrationTest#fetchByKeyWithUpdateAction()")
@HasCreateCommand(includeExamples = "io.sphere.sdk.productselections.commands.ProductSelectionCreateCommandIntegrationTest#execution()")
@HasUpdateCommand(javadocSummary = "Updates a cart.", updateWith = {"key","id"})
@HasDeleteCommand(javadocSummary = "Deletes a cart", deleteWith = {"key","id"})
@HasQueryModel
public interface ProductSelection extends Resource<ProductSelection>, WithKey, Custom {
    @Nullable
    String getKey();

    @HasUpdateAction
    LocalizedString getName();

    Long getProductCount();

    @IgnoreInQueryModel
    @Deprecated
    ProductSelectionType getType();

    @IgnoreInQueryModel
    ProductSelectionMode getMode();

    @IgnoreInQueryModel
    @Nullable
    LastModifiedBy getLastModifiedBy();

    @IgnoreInQueryModel
    @Nullable
    CreatedBy getCreatedBy();

    @Override
    default Reference<ProductSelection> toReference() {
        return reference(this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "product-selection";
    }

    static String resourceTypeId() {
        return "product-selection";
    }

    static Reference<ProductSelection> reference(final ProductSelection productType) {
        return Reference.of(referenceTypeId(), productType.getId(), productType);
    }

    static Reference<ProductSelection> reference(final String id) {
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
    static TypeReference<ProductSelection> typeReference() {
        return new TypeReference<ProductSelection>(){
            @Override
            public String toString() {
                return "TypeReference<ProductSelection>";
            }
        };
    }

    static Reference<ProductSelection> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
