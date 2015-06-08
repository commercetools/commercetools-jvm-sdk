package io.sphere.sdk.queries;

import java.util.List;

public interface FetchDsl<T, C> extends Fetch<T>, ReferenceExpandeableDsl<T, C> {
    /**
     * Returns a list of expansion paths for reference expansion.
     * @return list if expansion paths or an empty list
     */
    List<ExpansionPath<T>> expansionPaths();
}
