package io.sphere.sdk.states.queries;

import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

final class StateByIdGetImpl extends MetaModelGetDslImpl<State, State, StateByIdGet, StateExpansionModel<State>> implements StateByIdGet {
    StateByIdGetImpl(final String id) {
        super(id, StateEndpoint.ENDPOINT, StateExpansionModel.of(), StateByIdGetImpl::new);
    }

    public StateByIdGetImpl(MetaModelGetDslBuilder<State, State, StateByIdGet, StateExpansionModel<State>> builder) {
        super(builder);
    }
}
