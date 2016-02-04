package io.sphere.sdk.types.expansion;

import io.sphere.sdk.types.Type;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface TypeExpansionModel<T> {

    static TypeExpansionModel<Type> of() {
        return new TypeExpansionModelImpl<>();
    }
}
