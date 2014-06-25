package io.sphere.sdk.producttypes;

import io.sphere.sdk.queries.QueryDslImpl;

public class ProductTypeQuery extends QueryDslImpl<ProductType, ProductTypeImpl, ProductTypeQueryModel<ProductType>> {

    public ProductTypeQuery() {
        super(ProductTypeRequestDefaults.ENDPOINT, QueryDslImpl.<ProductType, ProductTypeImpl>resultMapperOf(ProductTypeRequestDefaults.PAGED_QUERY_RESULT_TYPE_REFERENCE));
    }
}
