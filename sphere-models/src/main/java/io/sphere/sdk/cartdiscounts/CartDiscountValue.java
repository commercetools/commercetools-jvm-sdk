package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RelativeCartDiscountValue.class, name = "relative"),
        @JsonSubTypes.Type(value = AbsoluteCartDiscountValue.class, name = "absolute") })
public interface CartDiscountValue {
}
