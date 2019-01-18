package io.sphere.sdk.customobjects.expansion;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.expansion.ExpansionModelImpl;

final class CustomObjectExpansionModelImpl<T> extends ExpansionModelImpl<CustomObject<T>> implements CustomObjectExpansionModel<T> {
    CustomObjectExpansionModelImpl() {
        super();
    }
}
