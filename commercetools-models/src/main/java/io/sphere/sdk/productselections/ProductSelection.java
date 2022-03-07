package io.sphere.sdk.productselections;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.*;

import javax.annotation.Nullable;

@JsonDeserialize(as= ProductSelectionImpl.class)
@ResourceValue
@ResourceInfo(pluralName = "product selections", pathElement = "product-selections")
public interface ProductSelection extends Resource<ProductSelection>, WithClientLogging, WithKey {
    @Nullable
    String getKey();
    /**
     * Name of this product selection.
     *
     * @see io.sphere.sdk.productselections.commands.updateactions.ChangeName
     *
     * @return name
     */
    @HasUpdateAction
    LocalizedString getName();

    Long getProductCount();

    ProductSelectionType getType();

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
    static Reference<ProductSelection> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
