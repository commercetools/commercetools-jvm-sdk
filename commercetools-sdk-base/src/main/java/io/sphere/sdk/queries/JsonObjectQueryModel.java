package io.sphere.sdk.queries;

public interface JsonObjectQueryModel<T> {
    StringQueryModel<T> ofString(String fieldName);

    JsonObjectQueryModel<T> ofJsonObject(String fieldName);

    BooleanQueryModel<T> ofBoolean(String fieldName);

    StringCollectionQueryModel<T> ofStringCollection(String fieldName);

    IntegerQueryModel<T> ofInteger(String fieldName);

    LongQueryModel<T> ofLong(String fieldName);
}
