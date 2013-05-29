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

public class ShippingMethodServiceImpl extends ProjectScopedAPI implements ShippingMethodService {
    private final RequestFactory requestFactory;
    private final static String expandZonesPath = "zoneRates[*].zone"; 

    public ShippingMethodServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    @Override public FetchRequest<ShippingMethod> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.shippingMethods.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<ShippingMethod>() {}).expand(expandZonesPath);
    }

    @Override public QueryRequest<ShippingMethod> all() {
        return requestFactory.createQueryRequest(
                endpoints.shippingMethods.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<ShippingMethod>>() {}).expand(expandZonesPath);
    }

    @Override
    public FetchRequest<List<ShippingMethod>> byLocation(Location location, Currency currency) {
        return requestFactory.createFetchRequest(
                endpoints.shippingMethods.byLocation(location.getCountry(), location.getState(), currency),
                Optional.<ApiMode>absent(),
                new TypeReference<List<ShippingMethod>>() {}).expand(expandZonesPath);   
    }

    @Override
    public FetchRequest<List<ShippingMethod>> byCart(String cartId) {
        return requestFactory.createFetchRequest(
                endpoints.shippingMethods.byCart(cartId),
                Optional.<ApiMode>absent(),
                new TypeReference<List<ShippingMethod>>() {}).expand(expandZonesPath);
    }
}
