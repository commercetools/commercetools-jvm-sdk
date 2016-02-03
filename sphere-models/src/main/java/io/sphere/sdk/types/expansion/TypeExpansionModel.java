package io.sphere.sdk.types.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.types.Type;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class TypeExpansionModel<T> extends ExpansionModel<T> {
    public TypeExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    TypeExpansionModel() {
        super();
    }

    public static TypeExpansionModel<Type> of() {
        return new TypeExpansionModel<>();
    }
}
