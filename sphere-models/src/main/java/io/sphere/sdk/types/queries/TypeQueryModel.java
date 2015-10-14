package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.types.Type;

public final class TypeQueryModel extends ResourceQueryModelImpl<Type> {

    public static TypeQueryModel of() {
        return new TypeQueryModel(null, null);
    }

    private TypeQueryModel(final QueryModel<Type> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<Type> key() {
        return stringModel("key");
    }
}
