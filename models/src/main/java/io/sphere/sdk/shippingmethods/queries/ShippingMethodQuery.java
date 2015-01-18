package io.sphere.sdk.shippingmethods.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.shippingmethods.ShippingMethod;

public class ShippingMethodQuery extends DefaultModelQuery<ShippingMethod> {
    private ShippingMethodQuery() {
        super(ShippingMethodEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<ShippingMethod>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ShippingMethod>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ShippingMethod>>";
            }
        };
    }

    public static ShippingMethodQuery of() {
        return new ShippingMethodQuery();
    }

    public static ShippingMethodQueryModel model() {
        return ShippingMethodQueryModel.get();
    }

    public QueryDsl<ShippingMethod> byName(final String name) {
        return withPredicate(model().name().is(name));
    }
}
