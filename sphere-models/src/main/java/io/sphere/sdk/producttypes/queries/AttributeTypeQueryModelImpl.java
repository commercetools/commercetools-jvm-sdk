package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQueryModel;

final class AttributeTypeQueryModelImpl<T> extends QueryModelImpl<T> implements AttributeTypeQueryModel<T> {
    AttributeTypeQueryModelImpl(QueryModel<T> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQueryModel<T> name() {
        return stringModel("name");
    }

    @Override
    public AttributeTypeQueryModel<T> type() {
        return new AttributeTypeQueryModelImpl<>(this, "elementType");
    }
}
