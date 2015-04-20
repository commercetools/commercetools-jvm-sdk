package io.sphere.sdk.states.queries;


import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.states.State;

import java.util.Optional;

public class StateQueryModel extends DefaultModelQueryModelImpl<State> {

    private StateQueryModel(final Optional<? extends QueryModel<State>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static StateQueryModel get() {
        return new StateQueryModel(Optional.<QueryModel<State>>empty(), Optional.<String>empty());
    }

    public StringQuerySortingModel<State> key() {
        return new StringQuerySortingModel<>(Optional.of(this), "key");
    }

}
