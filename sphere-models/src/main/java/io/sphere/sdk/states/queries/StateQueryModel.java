package io.sphere.sdk.states.queries;


import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.states.State;

public class StateQueryModel extends DefaultModelQueryModelImpl<State> {

    private StateQueryModel(final QueryModel<State> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static StateQueryModel of() {
        return new StateQueryModel(null, null);
    }

    public StringQuerySortingModel<State> key() {
        return stringModel("key");
    }

}
