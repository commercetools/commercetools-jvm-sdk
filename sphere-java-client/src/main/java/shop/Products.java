package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.model.Product;
import de.commercetools.sphere.client.util.RequestBuilder;

/** Wraps Sphere HTTP APIs for working with Products in a given project. */
public interface Products {
    /** Finds a product by id. */
    RequestBuilder<Product> byId(String id);

    /** Queries all products. */
    RequestBuilder<QueryResult<Product>> all();
}
