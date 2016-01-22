package io.sphere.sdk.states.queries;

import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.SphereEnumerationQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateType;

public interface StateQueryModel extends ResourceQueryModel<State> {
    StringQuerySortingModel<State> key();

    SphereEnumerationQueryModel<State, StateType> type();

    static StateQueryModel of() {
        return new StateQueryModelImpl(null, null);
    }
}
