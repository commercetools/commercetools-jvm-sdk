package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum TaxMode implements SphereEnumeration {
    /**
     * The tax rates are selected by the platform from the {@link TaxCategory tax categories} based on the cart shipping address.
     */
    PLATFORM,

    /**
     * The tax rates are set externally. A cart with this tax mode can only be ordered if all line items, all custom line items and the shipping method have an external tax rate set.
     */
    EXTERNAL,

    /**
     * No taxes are added to the cart.
     */
    DISABLED;
    @JsonCreator
    public static TaxMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
