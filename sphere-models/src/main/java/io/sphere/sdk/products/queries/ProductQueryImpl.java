package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 {@doc.gen summary products}
 */
final class ProductQueryImpl extends UltraQueryDslImpl<Product, ProductQuery, ProductQueryModel<Product>, ProductExpansionModel<Product>> implements ProductQuery {
    ProductQueryImpl(){
        super(ProductsEndpoint.ENDPOINT.endpoint(), ProductQuery.resultTypeReference(), ProductQueryModel.of(), ProductExpansionModel.of());
    }

    private ProductQueryImpl(final Optional<QueryPredicate<Product>> productProjectionQueryPredicate, final List<QuerySort<Product>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<Product>> resultMapper, final List<ExpansionPath<Product>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final ProductQueryModel<Product> queryModel, final ProductExpansionModel<Product> expansionModel) {
        super(productProjectionQueryPredicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<Product, ProductQuery, ProductQueryModel<Product>, ProductExpansionModel<Product>> copyBuilder() {
        return new ProductProjectionQueryQueryDslBuilder(this);
    }

    private static class ProductProjectionQueryQueryDslBuilder extends UltraQueryDslBuilder<Product, ProductQuery, ProductQueryModel<Product>, ProductExpansionModel<Product>> {
        public ProductProjectionQueryQueryDslBuilder(final UltraQueryDslImpl<Product, ProductQuery, ProductQueryModel<Product>, ProductExpansionModel<Product>> template) {
            super(template);
        }

        @Override
        public ProductQueryImpl build() {
            return new ProductQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}