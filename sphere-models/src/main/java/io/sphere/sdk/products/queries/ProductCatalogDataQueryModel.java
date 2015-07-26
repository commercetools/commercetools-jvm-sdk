package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.EmbeddedQueryPredicate;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.QueryPredicate;

import javax.annotation.Nullable;
import java.util.function.Function;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;

public class ProductCatalogDataQueryModel<M> extends QueryModelImpl<M> {

    ProductCatalogDataQueryModel(@Nullable final QueryModel<M> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductDataQueryModel<M> forProjection(final ProductProjectionType type) {
        return type == CURRENT ? current() : staged();
    }

    public ProductDataQueryModel<M> current() {
        return newProductDataQueryModel("current");
    }

    public ProductDataQueryModel<M> staged() {
        return newProductDataQueryModel("staged");
    }

    private ProductDataQueryModel<M> newProductDataQueryModel(final String pathSegment) {
        return new ProductDataQueryModel<>(this, pathSegment);
    }

    public QueryPredicate<M> where(final QueryPredicate<PartialProductCatalogDataQueryModel> embeddedPredicate) {
        return new EmbeddedQueryPredicate<>(this, embeddedPredicate);
    }

    public QueryPredicate<M> where(final Function<PartialProductCatalogDataQueryModel, QueryPredicate<PartialProductCatalogDataQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(PartialProductCatalogDataQueryModel.of()));
    }
}