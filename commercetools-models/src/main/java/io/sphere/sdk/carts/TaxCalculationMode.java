package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * A calculation mode specifies how the platform performs calculation of taxes.
 */
public enum TaxCalculationMode implements SphereEnumeration{

    /**
     * This calculation mode calculates the taxes after the unit price is multiplied with the quantity.<br>
     * {@code E.g. ($1.08 * 3 = $3.24) * 1.19 = $3.8556 -> $3.86 rounded}
     */
    LINE_ITEM_LEVEL,
    /**
     *This calculation mode calculates the taxes on the unit price before multiplying with the quantity.<br>
     *  {@code E.g. ($1.08 * 1.19 = $1.2852 -> $1.29 rounded) * 3 = $3.87}
     */
    UNIT_PRICE_LEVEL;


    @JsonCreator
    public static TaxCalculationMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }


}
