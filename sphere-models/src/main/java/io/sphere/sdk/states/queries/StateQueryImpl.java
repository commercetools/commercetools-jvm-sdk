package io.sphere.sdk.states.queries;


import io.sphere.sdk.queries.UltraQueryDslBuilder;
import io.sphere.sdk.queries.UltraQueryDslImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.queries.expansion.StateExpansionModel;

final class StateQueryImpl extends UltraQueryDslImpl<State, StateQuery, StateQueryModel<State>, StateExpansionModel<State>> implements StateQuery {
    StateQueryImpl(){
        super(StateEndpoint.ENDPOINT.endpoint(), StateQuery.resultTypeReference(), StateQueryModel.of(), StateExpansionModel.of(), StateQueryImpl::new);
    }

    private StateQueryImpl(final UltraQueryDslBuilder<State, StateQuery, StateQueryModel<State>, StateExpansionModel<State>> builder) {
        super(builder);
    }
}