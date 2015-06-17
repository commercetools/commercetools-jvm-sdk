package io.sphere.sdk.states.expansion;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.states.State;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class StateExpansionModel<T> extends ExpansionModel<T> {
    public StateExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    StateExpansionModel() {
        super();
    }

    public static StateExpansionModel<State> of() {
        return new StateExpansionModel<>();
    }
}
