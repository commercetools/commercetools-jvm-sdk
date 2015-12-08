package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.LongQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.states.State;

final class ItemStateCollectionQueryModelImpl<T> extends QueryModelImpl<T> implements ItemStateCollectionQueryModel<T> {

    public ItemStateCollectionQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceQueryModel<T, State> state() {
        return referenceModel("state");
    }

    @Override
    public LongQueryModel<T> quantity() {
        return longModel("quantity");
    }
}
