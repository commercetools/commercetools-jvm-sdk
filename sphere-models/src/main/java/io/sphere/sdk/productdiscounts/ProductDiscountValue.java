package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Defines discount type with the corresponding value. The type can be relative or absolute.
 *
 * @see RelativeProductDiscountValue
 * @see AbsoluteProductDiscountValue
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RelativeProductDiscountValue.class, name = "relative"),
        @JsonSubTypes.Type(value = AbsoluteProductDiscountValue.class, name = "absolute") })
public interface ProductDiscountValue {
}
