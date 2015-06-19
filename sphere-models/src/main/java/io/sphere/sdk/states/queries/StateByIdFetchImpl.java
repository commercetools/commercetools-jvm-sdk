package io.sphere.sdk.states.queries;

import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

final class StateByIdFetchImpl extends MetaModelFetchDslImpl<State, StateByIdFetch, StateExpansionModel<State>> implements StateByIdFetch {
    StateByIdFetchImpl(final String id) {
        super(id, StateEndpoint.ENDPOINT, StateExpansionModel.of(), StateByIdFetchImpl::new);
    }

    public StateByIdFetchImpl(MetaModelFetchDslBuilder<State, StateByIdFetch, StateExpansionModel<State>> builder) {
        super(builder);
    }
}
