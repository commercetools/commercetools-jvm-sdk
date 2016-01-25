package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.ReferenceOptionalQueryModel;
import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel;
import io.sphere.sdk.states.State;

public interface ProductQueryModel extends ResourceQueryModel<Product> {
    ProductCatalogDataQueryModel<Product> masterData();

    ReferenceQueryModel<Product, ProductType> productType();

    ReferenceOptionalQueryModel<Product, State> state();

    ReviewRatingStatisticsQueryModel<Product> reviewRatingStatistics();

    static ProductQueryModel of() {
        return new ProductQueryModelImpl(null, null);
    }
}
