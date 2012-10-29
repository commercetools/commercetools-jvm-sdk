package de.commercetools.internal;

import de.commercetools.internal.model.BackendCategory;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.QueryRequest;

/** Sphere HTTP APIs for working with Categories in a given project. */
public interface Categories {
    /** Queries all categories. */
    QueryRequest<QueryResult<BackendCategory>> all();
}
