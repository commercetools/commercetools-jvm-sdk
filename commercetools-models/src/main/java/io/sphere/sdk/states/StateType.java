package io.sphere.sdk.states;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * StateType.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 *
 */
public enum StateType implements SphereEnumeration {

    LINE_ITEM_STATE, ORDER_STATE, PRODUCT_STATE, REVIEW_STATE, PAYMENT_STATE;

    @JsonCreator
    public static StateType ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }

}
