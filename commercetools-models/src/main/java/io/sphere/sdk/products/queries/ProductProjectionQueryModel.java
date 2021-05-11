package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel;

public interface ProductProjectionQueryModel extends ResourceQueryModel<ProductProjection>, WithEmbeddedSharedProductProjectionProductDataQueryModel<ProductProjection> {
    ReferenceQueryModel<ProductProjection, ProductType> productType();

    BooleanQueryModel<ProductProjection> hasStagedChanges();

    @Override
    ReferenceCollectionQueryModel<ProductProjection, Category> categories();

    @Override
    LocalizedStringQueryModel<ProductProjection> description();

    @Override
    ProductAllVariantsQueryModel<ProductProjection> allVariants();

    @Override
    ProductVariantQueryModel<ProductProjection> masterVariant();

    @Override
    LocalizedStringQuerySortingModel<ProductProjection> metaDescription();

    @Override
    LocalizedStringQuerySortingModel<ProductProjection> metaKeywords();

    @Override
    LocalizedStringQuerySortingModel<ProductProjection> metaTitle();

    @Override
    LocalizedStringQuerySortingModel<ProductProjection> name();

    @Override
    LocalizedStringQuerySortingModel<ProductProjection> slug();

    @Override
    ProductVariantQueryModel<ProductProjection> variants();

    @Override
    CategoryOrderHintsQueryModel<ProductProjection> categoryOrderHints();

    @Override
    StringQuerySortingModel<ProductProjection> key();

    ReviewRatingStatisticsQueryModel<ProductProjection> reviewRatingStatistics();

    QueryPredicate<ProductProjection> is(Referenceable<Product> product);

    static ProductProjectionQueryModel of() {
        return new ProductProjectionQueryModelImpl(null, null);
    }
}
