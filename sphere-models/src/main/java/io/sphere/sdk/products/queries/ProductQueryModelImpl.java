package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

final class ProductQueryModelImpl extends ResourceQueryModelImpl<Product> implements ProductQueryModel {

    ProductQueryModelImpl(@Nullable final QueryModel<Product> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ProductCatalogDataQueryModel<Product> masterData() {
        return new ProductCatalogDataQueryModelImpl<>(this, "masterData");
    }

    @Override
    public ReferenceQueryModel<Product, ProductType> productType() {
        return referenceModel("productType");
    }


    @Override
    public ReferenceOptionalQueryModel<Product, State> state() {
        return referenceOptionalModel("state");
    }

    @Override
    public ReviewRatingStatisticsQueryModel<Product> reviewRatingStatistics() {
        return ReviewRatingStatisticsQueryModel.of(this, "reviewRatingStatistics");
    }
}