package io.sphere.client.shop;

import java.util.Currency;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.shop.model.Location;
import io.sphere.client.shop.model.ShippingMethod;

/** Sphere HTTP API for querying shipping methods in a given project. The zone references of the shipping method
 * query results are expanded.*/
public interface ShippingMethodService {
    /** Finds a shipping method by id. */
    FetchRequest<ShippingMethod> byId(String id);

    /** Queries all shipping methods in current project. */
    QueryRequest<ShippingMethod> all();

    /** Queries all shipping methods by location and currency in the current project. */
    public QueryRequest<ShippingMethod> byLocation(Location location, Currency currency);
}
