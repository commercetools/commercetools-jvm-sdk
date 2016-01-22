package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.StringCollectionQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.types.Type;

final class TypeQueryModelImpl extends ResourceQueryModelImpl<Type> implements TypeQueryModel {

    TypeQueryModelImpl(final QueryModel<Type> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<Type> key() {
        return stringModel("key");
    }

    @Override
    public StringCollectionQueryModel<Type> resourceTypeIds() {
        return stringCollectionModel("resourceTypeIds");
    }

    @Override
    public FieldDefinitionCollectionQueryModel<Type> fieldDefinitions() {
        return new FieldDefinitionCollectionQueryModelImpl<>(this, "fieldDefinitions");
    }

//it is not possible to query by name/description with the API
}
