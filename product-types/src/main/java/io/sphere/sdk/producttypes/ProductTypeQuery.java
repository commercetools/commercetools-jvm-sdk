package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDslImpl;

public class ProductTypeQuery extends QueryDslImpl<ProductType, ProductTypeImpl, ProductTypeQueryModel<ProductType>> {

    public ProductTypeQuery() {
        super("/product-types", QueryDslImpl.<ProductType, ProductTypeImpl>resultMapperOf(new TypeReference<PagedQueryResult<ProductTypeImpl>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductTypeImpl>>";
            }
        }));
    }
}
