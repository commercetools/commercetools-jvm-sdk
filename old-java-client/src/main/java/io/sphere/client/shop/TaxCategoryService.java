package io.sphere.client.shop;

import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.shop.model.TaxCategory;

/** Sphere HTTP API for working with tax categories in a given project. */
public interface TaxCategoryService {
    /** Finds a tax category by id. */
    FetchRequest<TaxCategory> byId(String id);

    /**
     * Queries all tax categories in current project.
     *
     * @deprecated since 0.49.0. Use {@link #query()} instead.
     **/
    @Deprecated
    QueryRequest<TaxCategory> all();

    /** Queries tax categories in current project. */
    QueryRequest<TaxCategory> query();
}
