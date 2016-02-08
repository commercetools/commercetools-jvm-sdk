package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.states.expansion.StateExpansionModel;

public interface ItemStateExpansionModel<T> {
    StateExpansionModel<T> state();
}
