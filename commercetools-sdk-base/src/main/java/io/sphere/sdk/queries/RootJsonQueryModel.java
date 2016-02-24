package io.sphere.sdk.queries;

public interface RootJsonQueryModel<T> {
    JsonQueryModel<T> ofObject();

    JsonValueQueryModel<T> ofValue();
}
