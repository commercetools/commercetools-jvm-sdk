package io.sphere.sdk.customobjects.expansion;

import io.sphere.sdk.customobjects.CustomObject;

/**
 * Placeholder for custom object reference expansion. Does not provide a functionality yet.
 *
 * @param <T> The type of the value of this custom object.
 */
public interface CustomObjectExpansionModel<T> {

    static <T> CustomObjectExpansionModel<CustomObject<T>> of(){
        return new CustomObjectExpansionModelImpl<>();
    }
}
