package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.RootJsonQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.StringQuerySortingModel;


final class CustomObjectQueryModelImpl<T extends CustomObject<?>> extends ResourceQueryModelImpl<T> implements CustomObjectQueryModel<T> {

    CustomObjectQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<T> container() {
        return stringModel("container");
    }

    @Override
    public StringQuerySortingModel<T> key() {
        return stringModel("key");
    }

    @Override
    public RootJsonQueryModel<T> value() {
        return new CustomObjectValueRootJsonObjectQueryModelImpl<>(null, "value");
    }
}
