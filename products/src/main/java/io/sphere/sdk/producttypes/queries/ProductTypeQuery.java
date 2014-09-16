package io.sphere.sdk.producttypes.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;

public class ProductTypeQuery extends DefaultModelQuery<ProductType> {

    public ProductTypeQuery() {
        super(ProductTypesEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<ProductType>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ProductType>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductType>>";
            }
        };
    }

    public QueryDsl<ProductType> byName(String name) {
        return withPredicate(model().name().is(name));
    }

    public static ProductTypeQuery of() {
        return new ProductTypeQuery();
    }

    public static ProductTypeQueryModel model() {
        return ProductTypeQueryModel.get();
    }
}
