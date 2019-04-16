package io.sphere.sdk.stores.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.stores.Store;

import java.util.List;

public interface StoreExpansionModel<T> extends ExpansionPathContainer<T> {

    static StoreExpansionModel<Store> of() {
        return new StoreExpansionModelImpl<>();
    }

    static <T> StoreExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new StoreExpansionModelImpl<>(parentPath, path);
    }
    
}
