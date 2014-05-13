package io.sphere.internal;

import io.sphere.client.model.products.BackendCategory;
import io.sphere.client.model.QueryResult;
import io.sphere.client.QueryRequest;

/** Sphere HTTP APIs for working with Categories in a given project. */
public interface Categories {
    /** Queries all categories. */
    QueryRequest<BackendCategory> query();
}
