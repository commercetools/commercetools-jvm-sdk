package io.sphere.sdk.states.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.states.State;

import java.util.List;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface StateExpansionModel<T> extends ExpansionPathContainer<T> {
    StateExpansionModel<T> transitions();

    static StateExpansionModel<State> of() {
        return new StateExpansionModelImpl<>();
    }

    static <T> StateExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new StateExpansionModelImpl<>(parentPath, path);
    }
}
