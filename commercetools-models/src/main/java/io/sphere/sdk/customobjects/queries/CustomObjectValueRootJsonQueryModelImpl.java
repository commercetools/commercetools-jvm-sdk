package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;

final class CustomObjectValueRootJsonQueryModelImpl<T> extends QueryModelImpl<T> implements RootJsonQueryModel<T>, JsonQueryModel<T>, JsonValueQueryModel<T> {
    CustomObjectValueRootJsonQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public JsonQueryModel<T> ofObject() {
        return new CustomObjectValueRootJsonQueryModelImpl<>(null, "value");
    }

    @Override
    public JsonValueQueryModel<T> ofValue() {
        return new CustomObjectValueRootJsonQueryModelImpl<>(null, null);
    }

    @Override
    public StringQueryModel<T> ofString(final String fieldName) {
        return stringModel(fieldName);
    }

    @Override
    public JsonQueryModel<T> ofObject(final String fieldName) {
        return new CustomObjectValueRootJsonQueryModelImpl<>(this, fieldName);
    }

    @Override
    public BooleanQueryModel<T> ofBoolean(final String fieldName) {
        return booleanModel(fieldName);
    }

    @Override
    public StringCollectionQueryModel<T> ofStringCollection(final String fieldName) {
        return super.stringCollectionModel(fieldName);
    }

    @Override
    public StringQueryModel<T> ofString() {
        return stringModel(null, "value");
    }
}
