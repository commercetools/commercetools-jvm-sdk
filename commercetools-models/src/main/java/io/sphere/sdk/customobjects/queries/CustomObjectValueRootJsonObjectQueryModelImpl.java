package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;

final class CustomObjectValueRootJsonObjectQueryModelImpl<T> extends QueryModelImpl<T> implements RootJsonQueryModel<T>, JsonObjectQueryModel<T>, JsonValueQueryModel<T> {

    public static final String VALUE = "value";

    CustomObjectValueRootJsonObjectQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public JsonObjectQueryModel<T> ofJsonObject() {
        return new CustomObjectValueRootJsonObjectQueryModelImpl<>(null, VALUE);
    }

    @Override
    public JsonValueQueryModel<T> ofJsonValue() {
        return new CustomObjectValueRootJsonObjectQueryModelImpl<>(null, null);
    }

    @Override
    public StringQueryModel<T> ofString(final String fieldName) {
        return stringModel(fieldName);
    }

    @Override
    public JsonObjectQueryModel<T> ofJsonObject(final String fieldName) {
        return new CustomObjectValueRootJsonObjectQueryModelImpl<>(this, fieldName);
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
    public IntegerQueryModel<T> ofInteger(final String fieldName) {
        return integerModel(fieldName);
    }

    @Override
    public LongQueryModel<T> ofLong(final String fieldName) {
        return longModel(fieldName);
    }

    @Override
    public StringQueryModel<T> ofString() {
        return stringModel(null, VALUE);
    }

    @Override
    public IntegerQueryModel<T> ofInteger() {
        return integerModel(null, VALUE);
    }

    @Override
    public LongQueryModel<T> ofLong() {
        return longModel(null, VALUE);
    }

    @Override
    public BooleanQueryModel<T> ofBoolean() {
        return booleanModel(null, VALUE);
    }
}
