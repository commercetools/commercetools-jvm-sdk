package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;

public class ProductQuery extends DefaultModelQuery<Product, ProductQueryModel<Product>> {
    ProductQuery(){
        super("/products", new TypeReference<PagedQueryResult<Product>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Product>>";
            }
        });
    }

    public QueryDsl<Product, ProductQueryModel<Product>> byName(final String name) {
        return withPredicate(ProductQueryModel.get().name().is(name));
    }
}