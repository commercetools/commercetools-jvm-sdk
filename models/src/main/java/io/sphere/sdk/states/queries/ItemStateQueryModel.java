package io.sphere.sdk.states.queries;


import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;

import java.util.Optional;

public class ItemStateQueryModel extends DefaultModelQueryModelImpl<ItemState> {

    private ItemStateQueryModel(final Optional<? extends QueryModel<ItemState>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static ItemStateQueryModel get() {
        return new ItemStateQueryModel(Optional.<QueryModel<WFItemState>>empty(), Optional.<String>empty());
    }

}
