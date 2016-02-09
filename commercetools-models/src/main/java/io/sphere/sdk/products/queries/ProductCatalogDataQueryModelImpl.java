package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.BooleanQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.QueryPredicate;

import javax.annotation.Nullable;
import java.util.function.Function;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;

class ProductCatalogDataQueryModelImpl<M> extends QueryModelImpl<M> implements ProductCatalogDataQueryModel<M> {

    ProductCatalogDataQueryModelImpl(@Nullable final QueryModel<M> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ProductDataQueryModel<M> forProjection(final ProductProjectionType type) {
        return type == CURRENT ? current() : staged();
    }

    @Override
    public ProductDataQueryModel<M> current() {
        return newProductDataQueryModel("current");
    }

    @Override
    public ProductDataQueryModel<M> staged() {
        return newProductDataQueryModel("staged");
    }

    @Override
    public BooleanQueryModel<M> isPublished() {
        return published();
    }

    @Override
    public BooleanQueryModel<M> published() {
        return booleanModel("published");
    }

    private ProductDataQueryModel<M> newProductDataQueryModel(final String pathSegment) {
        return new ProductDataQueryModelImpl<>(this, pathSegment);
    }

    @Override
    public QueryPredicate<M> where(final QueryPredicate<EmbeddedProductCatalogDataQueryModel> embeddedPredicate) {
        return embedPredicate(embeddedPredicate);
    }

    @Override
    public QueryPredicate<M> where(final Function<EmbeddedProductCatalogDataQueryModel, QueryPredicate<EmbeddedProductCatalogDataQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(EmbeddedProductCatalogDataQueryModel.of()));
    }
}