package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.*;

final class FieldDefinitionCollectionQueryModelImpl<T> extends QueryModelImpl<T> implements FieldDefinitionCollectionQueryModel<T> {
    public FieldDefinitionCollectionQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> isEmpty() {
        return isEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> isNotEmpty() {
        return isNotEmptyCollectionQueryPredicate();
    }

    @Override
    public StringQuerySortingModel<T> name() {
        return stringModel("name");
    }

    @Override
    public FieldTypeQueryModel<T> type() {
        return new FieldTypeQueryModelImpl<>(this, "type");
    }
}
