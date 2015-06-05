package io.sphere.sdk.states.queries;


import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.states.State;

import java.util.Optional;

public class StateQueryModel<T> extends DefaultModelQueryModelImpl<T> {

    private StateQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static StateQueryModel<State> of() {
        return new StateQueryModel<>(Optional.<QueryModel<State>>empty(), Optional.<String>empty());
    }

    public StringQuerySortingModel<T> key() {
        return new StringQuerySortingModel<>(Optional.of(this), "key");
    }

}
