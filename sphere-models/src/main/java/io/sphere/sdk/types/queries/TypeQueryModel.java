package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.StringCollectionQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.types.Type;

public interface TypeQueryModel extends ResourceQueryModel<Type> {
    StringQuerySortingModel<Type> key();

    StringCollectionQueryModel<Type> resourceTypeIds();

    FieldDefinitionCollectionQueryModel<Type> fieldDefinitions();

    static TypeQueryModel of() {
        return new TypeQueryModelImpl(null, null);
    }
}
