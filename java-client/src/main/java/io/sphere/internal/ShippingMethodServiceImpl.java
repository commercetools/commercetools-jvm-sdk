package io.sphere.internal;

import java.util.Currency;
import java.util.List;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.ShippingMethodService;
import io.sphere.client.shop.model.Location;
import io.sphere.client.shop.model.ShippingMethod;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;

public class ShippingMethodServiceImpl extends ProjectScopedAPI<ShippingMethod> implements ShippingMethodService {
    private final static String expandZonesPath = "zoneRates[*].zone";

    public ShippingMethodServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<ShippingMethod>() {}, new TypeReference<QueryResult<ShippingMethod>>() {});
    }

    @Override public FetchRequest<ShippingMethod> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.shippingMethods.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<ShippingMethod>() {}).expand(expandZonesPath);
    }

    @Deprecated
    @Override public QueryRequest<ShippingMethod> all() {
        return query();
    }

    @Override public QueryRequest<ShippingMethod> query() {
        return queryImpl(endpoints.shippingMethods.root()).expand(expandZonesPath);
    }

    @Override
    public FetchRequest<List<ShippingMethod>> forLocation(Location location, Currency currency) {
        return requestFactory.createFetchRequest(
                endpoints.shippingMethods.forLocation(location.getCountry(), location.getState(), currency),
                Optional.<ApiMode>absent(),
                new TypeReference<List<ShippingMethod>>() {}).expand(expandZonesPath);   
    }

    @Override
    public FetchRequest<List<ShippingMethod>> forCart(String cartId) {
        return requestFactory.createFetchRequest(
                endpoints.shippingMethods.forCart(cartId),
                Optional.<ApiMode>absent(),
                new TypeReference<List<ShippingMethod>>() {}).expand(expandZonesPath);
    }
}
