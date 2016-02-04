package io.sphere.sdk.states.expansion;

import io.sphere.sdk.expansion.ExpandedModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.states.State;

import java.util.List;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class StateExpansionModel<T> extends ExpandedModel<T> {
    public StateExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    StateExpansionModel() {
        super();
    }

    public static StateExpansionModel<State> of() {
        return new StateExpansionModel<>();
    }

    public static <T> StateExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new StateExpansionModel<>(parentPath, path);
    }

    public StateExpansionModel<T> transitions() {
        return StateExpansionModel.of(buildPathExpression(), "transitions[*]");
    }
}
