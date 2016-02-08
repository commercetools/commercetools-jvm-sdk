package io.sphere.sdk.reviews.expansion;

import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.states.expansion.StateExpansionModel;

final class ReviewExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ReviewExpansionModel<T> {
    ReviewExpansionModelImpl(final String parentPath, final String path) {
        super(parentPath, path);
    }

    ReviewExpansionModelImpl() {
        super();
    }

    @Override
    public CustomerExpansionModel<T> customer() {
        return CustomerExpansionModel.of(buildPathExpression(), "customer");
    }

    @Override
    public StateExpansionModel<T> state() {
        return StateExpansionModel.of(buildPathExpression(), "state");
    }

    @Override
    public ExpansionPathContainer<T> target() {
        return expansionPath("target");
    }
}
