package io.sphere.sdk.queries;

public interface SortingModel<T> {
    public abstract Sort sort(SortDirection sortDirection);
}