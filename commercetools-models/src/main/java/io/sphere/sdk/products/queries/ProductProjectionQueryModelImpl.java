package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel;

final class ProductProjectionQueryModelImpl extends ProductDataQueryModelBaseImpl<ProductProjection> implements ProductProjectionQueryModel {

    ProductProjectionQueryModelImpl(final QueryModel<ProductProjection> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceQueryModel<ProductProjection, ProductType> productType() {
        return referenceModel("productType");
    }

    @Override
    public BooleanQueryModel<ProductProjection> hasStagedChanges() {
        return booleanModel("hasStagedChanges");
    }

    @Override
    public ReferenceCollectionQueryModel<ProductProjection, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringQueryModel<ProductProjection> description() {
        return super.description();
    }

    @Override
    public ProductAllVariantsQueryModel<ProductProjection> allVariants() {
        return super.allVariants();
    }

    @Override
    public ProductVariantQueryModel<ProductProjection> masterVariant() {
        return super.masterVariant();
    }

    @Override
    public LocalizedStringQuerySortingModel<ProductProjection> metaDescription() {
        return super.metaDescription();
    }

    @Override
    public LocalizedStringQuerySortingModel<ProductProjection> metaKeywords() {
        return super.metaKeywords();
    }

    @Override
    public LocalizedStringQuerySortingModel<ProductProjection> metaTitle() {
        return super.metaTitle();
    }

    @Override
    public LocalizedStringQuerySortingModel<ProductProjection> name() {
        return super.name();
    }

    @Override
    public LocalizedStringQuerySortingModel<ProductProjection> slug() {
        return super.slug();
    }

    @Override
    public ProductVariantQueryModel<ProductProjection> variants() {
        return super.variants();
    }

    @Override
    public CategoryOrderHintsQueryModel<ProductProjection> categoryOrderHints() {
        return super.categoryOrderHints();
    }

    @Override
    public StringQuerySortingModel<ProductProjection> key() {
        return super.key();
    }

    @Override
    public ReviewRatingStatisticsQueryModel<ProductProjection> reviewRatingStatistics() {
        return ReviewRatingStatisticsQueryModel.of(this, "reviewRatingStatistics");
    }

    @Override
    public QueryPredicate<ProductProjection> is(final Referenceable<Product> product) {
        return id().is(product.toReference().getId());
    }
}