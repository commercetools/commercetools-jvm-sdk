package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQueryModel;

final class AttributeDefinitionQueryModelImpl<T> extends QueryModelImpl<T> implements AttributeDefinitionQueryModel<T> {

    AttributeDefinitionQueryModelImpl(QueryModel<T> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQueryModel<T> name() {
        return stringModel("name");
    }

    @Override
    public AttributeTypeQueryModel<T> type() {
        return new AttributeTypeQueryModelImpl<>(this, "type");
    }
}
