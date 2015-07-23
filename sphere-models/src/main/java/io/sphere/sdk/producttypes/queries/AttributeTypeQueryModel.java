package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQueryModel;

public final class AttributeTypeQueryModel<T> extends QueryModelImpl<T> {
    AttributeTypeQueryModel(QueryModel<T> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQueryModel<T> name() {
        return stringModel("name");
    }

    public AttributeTypeQueryModel<T> type() {
        return new AttributeTypeQueryModel<>(this, "elementType");
    }
}
