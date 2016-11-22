package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.money.MonetaryAmount;
import java.util.Collections;
import java.util.List;

/**
 * Defines discount type with the corresponding value. The type can be relative or absolute.
 *
 * @see RelativeProductDiscountValue
 * @see AbsoluteProductDiscountValue
 * @see ExternalProductDiscountValue
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RelativeProductDiscountValue.class, name = "relative"),
        @JsonSubTypes.Type(value = AbsoluteProductDiscountValue.class, name = "absolute"),
        @JsonSubTypes.Type(value = ExternalProductDiscountValue.class, name = "external")
})
public interface ProductDiscountValue {
    static AbsoluteProductDiscountValue ofAbsolute(final List<MonetaryAmount> money) {
        return AbsoluteProductDiscountValue.of(money);
    }

    static AbsoluteProductDiscountValue ofAbsolute(final MonetaryAmount money) {
        return AbsoluteProductDiscountValue.of(Collections.singletonList(money));
    }

    static RelativeProductDiscountValue ofRelative(final int permyriad) {
        return RelativeProductDiscountValue.of(permyriad);
    }

    static ExternalProductDiscountValue ofExternal() {
        return ExternalProductDiscountValue.of();
    }
}
