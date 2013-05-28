package io.sphere.client.shop;

import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.shop.model.TaxCategory;

/** Sphere HTTP API for working with tax categories in a given project. */
public interface TaxCategoryService {
    /** Finds a tax category by id. */
    FetchRequest<TaxCategory> byId(String id);

    /** Queries all tax categories in current project. */
    QueryRequest<TaxCategory> all();
}
