package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Cart discounts are used to change the prices of different elements within a cart like Line Items.
 *
 * @see io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommand
 * @see io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommand
 * @see io.sphere.sdk.cartdiscounts.commands.CartDiscountDeleteCommand
 * @see io.sphere.sdk.cartdiscounts.queries.CartDiscountQuery
 * @see io.sphere.sdk.cartdiscounts.queries.CartDiscountByIdGet
 * @see DiscountCode#getCartDiscounts()
 */
@JsonDeserialize(as=CartDiscountImpl.class)
@ResourceValue
@HasQueryEndpoint()
@ResourceInfo(pluralName = "cart discounts", pathElement = "cart-discounts")
@HasByIdGetEndpoint(javadocSummary = "Gets a {@link CartDiscount} by a known ID.", includeExamples = "io.sphere.sdk.cartdiscounts.queries.CartDiscountByIdGetIntegrationTest#execution()")
@HasCreateCommand(includeExamples = "io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommandIntegrationTest#execution()")
@HasUpdateCommand
@HasDeleteCommand(javadocSummary = "Deletes a {@link CartDiscount}.")
@HasQueryModel
public interface CartDiscount extends Resource<CartDiscount> {
    /**
     * Predicate where the discounts should be applied to.
     *
     * @see #isRequiringDiscountCode()
     * @see CartDiscountPredicate
     *
     * @return predicate
     */
    @IgnoreInQueryModel
    String getCartPredicate();

    /**
     * Description of this cart discount.
     *
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetDescription
     *
     * @return description or null
     */
    @Nullable
    @IgnoreInQueryModel
    LocalizedString getDescription();

    /**
     * Flag if the discount is active.
     *
     * @see io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeIsActive
     *
     * @return true if active
     */
    @JsonProperty("isActive")
    @IgnoreInQueryModel
    Boolean isActive();

    /**
     * Name of this discount.
     *
     * @see io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeName
     *
     * @return name
     */
    LocalizedString getName();

    @IgnoreInQueryModel
    List<Reference<JsonNode>> getReferences();

    /**
     * States whether the the discount code can only be used in a connection with a {@link DiscountCode}.
     *
     * @see io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeRequiresDiscountCode
     *
     * @return true if requires a discount code
     */
    @JsonProperty("requiresDiscountCode")
    @IgnoreInQueryModel
    Boolean isRequiringDiscountCode();

    /**
     * Determinates the order of multiple cart discounts.
     *
     * The string must contain a number between 0 and 1. All matching cart discounts are applied to a cart in the order defined by this field. The sortOrder must be unambiguous among all cart discounts.
     *
     * @see io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeSortOrder
     *
     * @return sort order
     */
    @IgnoreInQueryModel
    String getSortOrder();

    /**
     * Defines what part of the cart will be discounted.
     *
     * @see io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeTarget
     *
     * @return the target or null if {@link #getValue()} is a {@link GiftLineItemCartDiscountValue}
     */
    @Nullable
    @IgnoreInQueryModel
    CartDiscountTarget getTarget();

    /**
     * Lower bound of the validity period.
     *
     * @see io.sphere.sdk.cartdiscounts.commands.updateactions.SetValidFrom
     *
     * @return valid from date or null
     */
    @Nullable
    @IgnoreInQueryModel
    ZonedDateTime getValidFrom();

    /**
     * Upper bound of the validity period.
     *
     * @see io.sphere.sdk.cartdiscounts.commands.updateactions.SetValidUntil
     *
     * @return valid to date or null
     */
    @Nullable
    @IgnoreInQueryModel
    ZonedDateTime getValidUntil();

    /**
     * The value of the discount (determines the reduced price).
     *
     * @see io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeValue
     *
     * @return value
     */
    @IgnoreInQueryModel
    CartDiscountValue getValue();

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "cart-discount";
    }

    @Override
    default Reference<CartDiscount> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
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
    static TypeReference<CartDiscount> typeReference() {
        return new TypeReference<CartDiscount>() {
            @Override
            public String toString() {
                return "TypeReference<CartDiscount>";
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
    static Reference<CartDiscount> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
