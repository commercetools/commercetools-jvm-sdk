package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.PagedQueryResult;

class ProductTypeRequestDefaults {

    public static final String ENDPOINT = "/product-types";
    public static final TypeReference<PagedQueryResult<ProductTypeImpl>> PAGED_QUERY_RESULT_TYPE_REFERENCE = new TypeReference<PagedQueryResult<ProductTypeImpl>>(){
        @Override
        public String toString() {
            return "TypeReference<PagedQueryResult<ProductTypeImpl>>";
        }
    };

    static final TypeReference<ProductTypeImpl> PRODUCT_TYPE_TYPE_REFERENCE = new TypeReference<ProductTypeImpl>() {
        @Override
        public String toString() {
            return "TypeReference<ProductTypeImpl>";
        }
    };

}
