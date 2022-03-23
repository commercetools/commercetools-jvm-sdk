package io.sphere.sdk.productselections;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.*;
import io.sphere.sdk.types.Custom;

import javax.annotation.Nullable;

@JsonDeserialize(as= ProductSelectionImpl.class)
@ResourceValue
@HasQueryEndpoint(additionalContentsQueryInterface = {
        "    default ProductSelectionQuery byName(final Locale locale, final String name) {\n" +
        "        return withPredicates(m -> m.name().lang(locale).is(name));\n" +
        "    }\n" +
        "\n" +
        "    default ProductSelectionQuery byKey(final String key) {\n" +
        "        return withPredicates(m -> m.key().is(key));\n" +
        "    }\n"
     })
@ResourceInfo(pluralName = "product selections", pathElement = "product-selections")
@HasByIdGetEndpoint
@HasByKeyGetEndpoint
@HasCreateCommand(includeExamples = "io.sphere.sdk.productselections.commands.ProductSelectionCreateCommandIntegrationTest#execution()")
@HasUpdateCommand(updateWith = {"key","id"})
@HasDeleteCommand(deleteWith = {"key","id"})
@HasQueryModel
public interface ProductSelection extends Resource<ProductSelection>, WithKey, Custom {
    @Nullable
    String getKey();

    @HasUpdateAction
    LocalizedString getName();

    Long getProductCount();

    @IgnoreInQueryModel
    ProductSelectionType getType();

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
