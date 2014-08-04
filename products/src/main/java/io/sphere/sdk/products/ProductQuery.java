package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;

public class ProductQuery extends DefaultModelQuery<Product, ProductQueryModel<Product>> {
    public ProductQuery(){
        super("/products", resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<Product>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Product>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Product>>";
            }
        };
    }

    public static ProductQuery of() {
        return new ProductQuery();
    }
}