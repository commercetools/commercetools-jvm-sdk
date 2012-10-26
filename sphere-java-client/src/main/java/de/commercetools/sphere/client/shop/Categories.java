package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.model.Category;
import de.commercetools.sphere.client.QueryRequest;

/** Sphere HTTP APIs for working with Categories in a given project. */
public interface Categories {
    /** Queries all categories. */
    QueryRequest<QueryResult<Category>> all();
}
