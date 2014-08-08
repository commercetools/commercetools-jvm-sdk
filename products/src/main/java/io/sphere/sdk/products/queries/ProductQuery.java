package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;

import java.util.Locale;

public class ProductQuery extends DefaultModelQuery<Product> {
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

    public QueryDsl<Product> bySlug(final ProductProjectionType type, final Locale locale, final String slug) {
        return withPredicate(ProductQueryModel.get().masterData().forProjection(type).slug().lang(locale).is(slug));
    }

    public QueryDsl<Product> byProductType(final Referenceable<ProductType> productType) {
        return withPredicate(ProductQueryModel.get().productType().is(productType));
    }

    public static ProductExpansionModel expansionPath() {
        return new ProductExpansionModel();
    }
}