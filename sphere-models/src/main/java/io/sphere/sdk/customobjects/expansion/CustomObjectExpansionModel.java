package io.sphere.sdk.customobjects.expansion;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.expansion.ExpansionModel;

public class CustomObjectExpansionModel<T> extends ExpansionModel<T> {
    private CustomObjectExpansionModel() {
        super();
    }

    public static <T> CustomObjectExpansionModel<CustomObject<T>> of(){
        return new CustomObjectExpansionModel<>();
    }
}
