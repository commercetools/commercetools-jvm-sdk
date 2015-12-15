package io.sphere.sdk.states.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathsHolder;
import io.sphere.sdk.states.State;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class StateExpansionModel<T> extends ExpansionModel<T> {
    public StateExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    StateExpansionModel() {
        super();
    }

    public static StateExpansionModel<State> of() {
        return new StateExpansionModel<>();
    }

    public ExpansionPathsHolder<T> transitions() {
        return expansionPath("transitions[*]");
    }
}
