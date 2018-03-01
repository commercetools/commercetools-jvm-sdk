package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Defines what part of the cart will be discounted.
 *
 * @see CartDiscount
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LineItemsTarget.class, name = "lineItems"),
        @JsonSubTypes.Type(value = CustomLineItemsTarget.class, name = "customLineItems"),
        @JsonSubTypes.Type(value = ShippingCostTarget.class, name = "shipping"),
        @JsonSubTypes.Type(value = MultiBuyCustomLineItemsTarget.class, name = "multiBuyCustomLineItems"),
        @JsonSubTypes.Type(value = MultiBuyLineItemsTarget.class, name = "multiBuyLineItems")})
public interface CartDiscountTarget {
}
