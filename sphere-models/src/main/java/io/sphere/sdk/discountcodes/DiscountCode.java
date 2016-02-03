package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
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
public interface DiscountCode extends Resource<DiscountCode> {
    /**
     * The referenced matching cart discounts can be applied to the cart once the discount code is added ({@link io.sphere.sdk.carts.commands.updateactions.AddDiscountCode}).
     * @return cart discounts
     * @see io.sphere.sdk.discountcodes.commands.updateactions.ChangeCartDiscounts
     */
    List<Reference<CartDiscount>> getCartDiscounts();

    /**
     * The discount code can only be applied to carts that match this predicate.
     *
     * @return predicate or null
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetCartPredicate
     */
    @Nullable
    String getCartPredicate();

    /**
     * Unique identifier of this discount code. This value is added to the cart ({@link io.sphere.sdk.carts.commands.updateactions.AddDiscountCode}) to enable the related cart discounts in the cart.
     * @return code
     */
    String getCode();

    /**
     * Description of this discount code.
     * @return description or null
     *
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetDescription
     */
    @Nullable
    LocalizedString getDescription();

    /**
     * Flag if this cart discount is enabled
     *
     * @return
     * @see io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeIsActive
     */
    Boolean isActive();

    /**
     * The discount code can only be applied maxApplications times.
     * @return max applications or null
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetMaxApplications
     */
    @Nullable
    Long getMaxApplications();

    /**
     * The discount code can only be applied maxApplicationsPerCustomer times per customer.
     * @return max applications or null
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetMaxApplicationsPerCustomer
     */
    @Nullable
    Long getMaxApplicationsPerCustomer();

    /**
     * Name of this discount code.
     *
     * @return name or null
     *
     * @see io.sphere.sdk.discountcodes.commands.updateactions.SetName
     */
    @Nullable
    LocalizedString getName();

    /**
     * The backend will generate this array from the cartPredicate. It contains the references of all the resources that are addressed in the predicate.
     * @return references
     */
    List<Reference<JsonNode>> getReferences();

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
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId(){
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
}
