package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.money.MonetaryAmount;
import java.util.Collections;
import java.util.List;

/**
 * Defines cart discount type with the corresponding value. The type can be relative or absolute.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RelativeCartDiscountValue.class, name = "relative"),
        @JsonSubTypes.Type(value = AbsoluteCartDiscountValue.class, name = "absolute") })
public interface CartDiscountValue {
        static AbsoluteCartDiscountValue ofAbsolute(final List<MonetaryAmount> money) {
                return AbsoluteCartDiscountValue.of(money);
        }

        static AbsoluteCartDiscountValue ofAbsolute(final MonetaryAmount money) {
                return AbsoluteCartDiscountValue.of(Collections.singletonList(money));
        }

        static RelativeCartDiscountValue ofRelative(final int permyriad) {
                return RelativeCartDiscountValue.of(permyriad);
        }
}
