package io.sphere.sdk.queries;

public interface SortingModel<T> {
    public abstract Sort<T> sort(SortDirection sortDirection);
}