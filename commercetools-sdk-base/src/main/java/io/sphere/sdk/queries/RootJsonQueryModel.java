package io.sphere.sdk.queries;

public interface RootJsonQueryModel<T> {
    JsonObjectQueryModel<T> ofJsonObject();

    JsonValueQueryModel<T> ofJsonValue();
}
