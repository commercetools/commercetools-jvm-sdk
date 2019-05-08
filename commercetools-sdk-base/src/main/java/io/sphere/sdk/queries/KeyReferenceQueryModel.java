package io.sphere.sdk.queries;

public interface KeyReferenceQueryModel<T> extends QueryModel<T> {
    
    StringQueryModel<T> key();

    StringQueryModel<T> typeId();
}
