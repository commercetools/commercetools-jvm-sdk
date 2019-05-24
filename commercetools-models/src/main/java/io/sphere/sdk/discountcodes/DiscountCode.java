package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Discount codes can be added to a cart to enable certain cart discounts.
 *
 * @see io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommand
 * @see io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommand
 * @see io.sphere.sdk.discountcodes.commands.DiscountCodeDeleteCommand
 * @see io.sphere.sdk.discountcodes.queries.DiscountCodeQuery
 * @see io.sphere.sdk.discountcodes.queries.DiscountCodeByIdGet
 * @see CartDiscount#isRequiringDiscountCode()
 */
@JsonDeserialize(as = DiscountCodeImpl.class)
@ResourceValue
@HasQueryEndpoint()
@ResourceInfo(pluralName = "discount codes", pathElement = "discount-codes")
@HasByIdGetEndpoint
@HasCreateCommand(includeExamples = "io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommandIntegrationTest#execution()")
@HasUpdateCommand
@HasDeleteCommand(canEraseUsersData = true)
@HasQueryModel
public interface DiscountCode extends Resource<DiscountCode>, Custom {
    /**
     * The referenced matching cart discounts can be applied to the cart once the discount code is added ({@link io.sphere.sdk.carts.commands.updateactions.AddDiscountCode}).
     * @return cart discounts
     * @see io.sphere.sdk.discountcodes.commands.updateactions.ChangeCartDiscounts
     */
    @IgnoreInQueryModel
    List<Reference<CartDiscount>> getCartDiscounts();

    /**
     * The discount code can only be applied to carts that match this predicate.
     *
     * @return predicate or null
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetCartPredicate
     */
    @Nullable
    @IgnoreInQueryModel
    String getCartPredicate();

    /**
     * Unique identifier of this discount code. This value is added to the cart ({@link io.sphere.sdk.carts.commands.updateactions.AddDiscountCode}) to enable the related cart discounts in the cart.
     * @return code
     */
    String getCode();


    /**
     * This field is used to signal to which groups the {@link DiscountCode} belongs to.
     * @return the groups, the discount code belongs to.
     */
    @HasUpdateAction
    List<String> getGroups();


    /**
     * Description of this discount code.
     * @return description or null
     *
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetDescription
     */
    @Nullable
    @IgnoreInQueryModel
    LocalizedString getDescription();

    /**
     * Flag if this cart discount is enabled
     *
     * @return true if enabled
     * @see io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeIsActive
     */
    @JsonProperty("isActive")
    @IgnoreInQueryModel
    Boolean isActive();

    /**
     * Start date for discount code validity
     * @return the validity start date
     */
    @Nullable
    @IgnoreInQueryModel
    @HasUpdateAction
    @HasUpdateAction(value = "setValidFromAndUntil", fields = {
            @PropertySpec(name = "validFrom", type = ZonedDateTime.class, isOptional = true),
            @PropertySpec(name = "validUntil", type = ZonedDateTime.class, isOptional = true)})
    ZonedDateTime getValidFrom();

    /**
     * End date for discount code validity
     * @return the validity limit date
     */
    @HasUpdateAction
    @Nullable
    @IgnoreInQueryModel
    ZonedDateTime getValidUntil();

    /**
     * The discount code can only be applied maxApplications times.
     * @return max applications or null
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetMaxApplications
     */
    @Nullable
    @IgnoreInQueryModel
    Long getMaxApplications();

    /**
     * The discount code can only be applied maxApplicationsPerCustomer times per customer.
     * @return max applications or null
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetMaxApplicationsPerCustomer
     */
    @Nullable
    @IgnoreInQueryModel
    Long getMaxApplicationsPerCustomer();

    /**
     * Name of this discount code.
     *
     * @return name or null
     *
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetName
     */
    @Nullable
    @IgnoreInQueryModel
    LocalizedString getName();

    /**
     * The backend will generate this array from the cartPredicate. It contains the references of all the resources that are addressed in the predicate.
     * @return references
     */
    @IgnoreInQueryModel
    List<Reference<JsonNode>> getReferences();

    @Nullable
    @Override
    CustomFields getCustom();

    @Override
    default Reference<DiscountCode> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "discount-code";
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
    static TypeReference<DiscountCode> typeReference() {
        return new TypeReference<DiscountCode>() {
            @Override
            public String toString() {
                return "TypeReference<DiscountCode>";
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
    static Reference<DiscountCode> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }


    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "discount-code";
    }
}
