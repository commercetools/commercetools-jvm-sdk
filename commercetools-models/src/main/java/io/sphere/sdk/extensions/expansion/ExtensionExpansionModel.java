package io.sphere.sdk.extensions.expansion;

import io.sphere.sdk.extensions.Extension;

public interface ExtensionExpansionModel<T> {
    static ExtensionExpansionModel<Extension> of() {
        return new ExtensionExpansionModelImpl<>();
    }
}
