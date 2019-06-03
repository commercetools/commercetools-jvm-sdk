package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Product discounts are used to change certain product prices.
 *
 * @see io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommand
 * @see io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommand
 * @see io.sphere.sdk.productdiscounts.commands.ProductDiscountDeleteCommand
 * @see io.sphere.sdk.productdiscounts.queries.ProductDiscountQuery
 * @see io.sphere.sdk.productdiscounts.queries.ProductDiscountByIdGet
 */
@JsonDeserialize(as=ProductDiscountImpl.class)
@ResourceValue
@HasQueryEndpoint()
@ResourceInfo(pluralName = "product discounts", pathElement = "product-discounts")
@HasByIdGetEndpoint
@HasByKeyGetEndpoint
@HasCreateCommand(includeExamples = "io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommandIntegrationTest#execution()")
@HasUpdateCommand(updateWith = "key")
@HasDeleteCommand(deleteWith = {"key","id"})
@HasQueryModel(additionalContents = "    default BooleanQueryModel<ProductDiscount> isActive() {\n" +
        "        return active();\n" +
        "    }")
public interface ProductDiscount extends Resource<ProductDiscount>, WithKey {

    @HasUpdateAction
    @Nullable
    String getKey();
    
    LocalizedString getName();

    @Nullable
    @IgnoreInQueryModel
    LocalizedString getDescription();

    @IgnoreInQueryModel
    ProductDiscountValue getValue();

    @IgnoreInQueryModel
    String getPredicate();

    @IgnoreInQueryModel
    String getSortOrder();

    @JsonProperty("isActive")
    Boolean isActive();

    @Nullable
    @HasUpdateAction
    @HasUpdateAction(value = "setValidFromAndUntil", fields = {
                    @PropertySpec(name = "validFrom",type=ZonedDateTime.class,isOptional = true),
                    @PropertySpec(name = "validUntil",type=ZonedDateTime.class, isOptional = true)
    })
    ZonedDateTime getValidFrom();

    @Nullable
    @HasUpdateAction
    ZonedDateTime getValidUntil();

    @IgnoreInQueryModel
    List<Reference<JsonNode>> getReferences();

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "product-discount";
    }

    static String resourceTypeId() { return "product-discount";}

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
    static TypeReference<ProductDiscount> typeReference() {
        return new TypeReference<ProductDiscount>() {
            @Override
            public String toString() {
                return "TypeReference<ProductDiscount>";
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
    static Reference<ProductDiscount> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    @Override
    default Reference<ProductDiscount> toReference() {
        return Reference.of(ProductDiscount.referenceTypeId(), this);
    }
}
