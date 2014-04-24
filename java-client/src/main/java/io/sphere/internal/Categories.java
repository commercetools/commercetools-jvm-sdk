package io.sphere.internal;

import io.sphere.client.model.products.BackendCategory;
import io.sphere.client.model.QueryResult;
import io.sphere.client.QueryRequest;

/** Sphere HTTP APIs for working with Categories in a given project. */
public interface Categories {
    /**
     * Queries categories. This method does not fetch all categories by default.
     * @deprecated since 0.56.0, use {@link Categories#query() instead}
     **/
    @Deprecated
    QueryRequest<BackendCategory> all();

    /** Queries all categories. */
    QueryRequest<BackendCategory> query();
}
