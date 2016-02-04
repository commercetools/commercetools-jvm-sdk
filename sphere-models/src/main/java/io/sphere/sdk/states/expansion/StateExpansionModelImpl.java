package io.sphere.sdk.states.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class StateExpansionModelImpl<T> extends ExpansionModelImpl<T> implements StateExpansionModel<T> {
    StateExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    StateExpansionModelImpl() {
        super();
    }

    @Override
    public StateExpansionModel<T> transitions() {
        return StateExpansionModel.of(buildPathExpression(), "transitions[*]");
    }
}
