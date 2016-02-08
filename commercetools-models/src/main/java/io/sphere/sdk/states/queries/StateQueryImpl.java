package io.sphere.sdk.states.queries;


import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

final class StateQueryImpl extends MetaModelQueryDslImpl<State, StateQuery, StateQueryModel, StateExpansionModel<State>> implements StateQuery {
    StateQueryImpl(){
        super(StateEndpoint.ENDPOINT.endpoint(), StateQuery.resultTypeReference(), StateQueryModel.of(), StateExpansionModel.of(), StateQueryImpl::new);
    }

    private StateQueryImpl(final MetaModelQueryDslBuilder<State, StateQuery, StateQueryModel, StateExpansionModel<State>> builder) {
        super(builder);
    }
}