package io.sphere.sdk.states.queries;


import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.SphereEnumerationQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateType;

public class StateQueryModel extends ResourceQueryModelImpl<State> {

    private StateQueryModel(final QueryModel<State> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static StateQueryModel of() {
        return new StateQueryModel(null, null);
    }

    public StringQuerySortingModel<State> key() {
        return stringModel("key");
    }

    public SphereEnumerationQueryModel<State, StateType> type() {
        return enumerationQueryModel("type");
    }
}
