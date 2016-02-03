package io.sphere.sdk.customobjects.expansion;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.expansion.ExpansionModel;

/**
 * Placeholder for custom object reference expansion. Does not provide a functionality yet.
 *
 * @param <T> The type of the value of this custom object.
 */
public final class CustomObjectExpansionModel<T> extends ExpansionModel<T> {
    private CustomObjectExpansionModel() {
        super();
    }

    public static <T> CustomObjectExpansionModel<CustomObject<T>> of(){
        return new CustomObjectExpansionModel<>();
    }
}
