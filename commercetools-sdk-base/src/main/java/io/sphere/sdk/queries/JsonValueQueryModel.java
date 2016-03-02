package io.sphere.sdk.queries;

public interface JsonValueQueryModel<T> {
    StringQueryModel<T> ofString();

    IntegerQueryModel<T> ofInteger();

    LongQueryModel<T> ofLong();

    BooleanQueryModel<T> ofBoolean();
}
