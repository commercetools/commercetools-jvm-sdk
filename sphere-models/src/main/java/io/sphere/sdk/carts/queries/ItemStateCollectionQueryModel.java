package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.LongQueryModel;
import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.states.State;

public interface ItemStateCollectionQueryModel<T> {
    ReferenceQueryModel<T, State> state();

    LongQueryModel<T> quantity();
}
