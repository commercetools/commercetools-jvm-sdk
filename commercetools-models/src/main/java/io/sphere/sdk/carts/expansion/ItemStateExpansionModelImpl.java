package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.states.expansion.StateExpansionModel;

import java.util.List;

final class ItemStateExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ItemStateExpansionModel<T> {
    ItemStateExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public StateExpansionModel<T> state() {
        return StateExpansionModel.of(buildPathExpression(), "state");
    }
}
