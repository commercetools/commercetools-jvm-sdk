package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.model.Category;
import de.commercetools.sphere.client.util.RequestBuilder;

/** Wraps Sphere HTTP APIs for working with Categories in a given project. */
public interface Categories {
    /** Queries all categories. */
    RequestBuilder<QueryResult<Category>> all();
}
