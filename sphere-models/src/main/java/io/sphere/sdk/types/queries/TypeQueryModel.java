package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.*;
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

    public StringCollectionQueryModel<Type> resourceTypeIds() {
        return stringCollectionModel("resourceTypeIds");
    }

//it is not possible to query by name/description with the API
}
