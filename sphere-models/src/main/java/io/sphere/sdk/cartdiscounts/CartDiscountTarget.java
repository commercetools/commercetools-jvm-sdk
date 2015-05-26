package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LineItemsTarget.class, name = "lineItems"),
        @JsonSubTypes.Type(value = CustomLineItemsTarget.class, name = "customLineItems"),
        @JsonSubTypes.Type(value = ShippingCostTarget.class, name = "shipping") })
public interface CartDiscountTarget {
}
