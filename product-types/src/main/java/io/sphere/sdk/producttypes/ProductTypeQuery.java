package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;

public class ProductTypeQuery extends DefaultModelQuery<ProductType, ProductTypeImpl, ProductTypeQueryModel<ProductType>> {

    public ProductTypeQuery() {
        super("/product-types", new TypeReference<PagedQueryResult<ProductTypeImpl>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductTypeImpl>>";
            }
        });
    }

    public QueryDsl<ProductType, ProductTypeQueryModel<ProductType>> byName(String name) {
        return withPredicate(ProductTypeQueryModel.get().name().is(name));
    }
}
