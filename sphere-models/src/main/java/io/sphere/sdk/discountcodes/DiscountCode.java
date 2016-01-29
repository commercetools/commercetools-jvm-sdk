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
 * @see io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommand
 * @see CartDiscount#isRequiringDiscountCode()
 */
@JsonDeserialize(as = DiscountCodeImpl.class)
public interface DiscountCode extends Resource<DiscountCode> {
    List<Reference<CartDiscount>> getCartDiscounts();

    @Nullable
    String getCartPredicate();

    String getCode();

    @Nullable
    LocalizedString getDescription();

    Boolean isActive();

    @Nullable
    Long getMaxApplications();

    @Nullable
    Long getMaxApplicationsPerCustomer();

    @Nullable
    LocalizedString getName();

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

    static TypeReference<DiscountCode> typeReference() {
        return new TypeReference<DiscountCode>() {
            @Override
            public String toString() {
                return "TypeReference<DiscountCode>";
            }
        };
    }

    static Reference<DiscountCode> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
