package io.sphere.sdk.queries;

public interface JsonQueryModel<T> {
    StringQueryModel<T> ofString(String fieldName);

    JsonQueryModel<T> ofObject(String fieldName);

    BooleanQueryModel<T> ofBoolean(String fieldName);

    StringCollectionQueryModel<T> ofStringCollection(String fieldName);
}
