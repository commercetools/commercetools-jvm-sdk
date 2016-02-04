package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.states.expansion.StateExpansionModel;

import java.util.List;

public final class ItemStateExpansionModel<T> extends ExpansionModel<T> {
    ItemStateExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    public StateExpansionModel<T> state() {
        return StateExpansionModel.of(buildPathExpression(), "state");
    }
}
