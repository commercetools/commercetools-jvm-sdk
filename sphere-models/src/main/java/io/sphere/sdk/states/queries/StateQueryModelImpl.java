package io.sphere.sdk.states.queries;


import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.SphereEnumerationQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateType;

final class StateQueryModelImpl extends ResourceQueryModelImpl<State> implements StateQueryModel {

    StateQueryModelImpl(final QueryModel<State> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<State> key() {
        return stringModel("key");
    }

    @Override
    public SphereEnumerationQueryModel<State, StateType> type() {
        return enumerationQueryModel("type");
    }
}
