package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


/** {@doc.gen summary product types}

  Query all product types:

  {@include.example example.QueryProductTypeExamples#queryAll()}

  Scenario to load a specific product type:

  {@include.example example.QueryByProductTypeNameExample}

  With {@link ProductTypeQueryModel} you can query for product types containing specific attributes:

  {@include.example example.QueryProductTypeExamples#queryByAttributeName()}

 */
final class ProductTypeQueryImpl extends UltraQueryDslImpl<ProductType, ProductTypeQuery, ProductTypeQueryModel<ProductType>, ProductTypeExpansionModel<ProductType>> implements ProductTypeQuery {
    ProductTypeQueryImpl(){
        super(ProductTypeEndpoint.ENDPOINT.endpoint(), ProductTypeQuery.resultTypeReference(), ProductTypeQueryModel.of(), ProductTypeExpansionModel.of());
    }

    private ProductTypeQueryImpl(final Optional<QueryPredicate<ProductType>> predicate, final List<QuerySort<ProductType>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<ProductType>> resultMapper, final List<ExpansionPath<ProductType>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final ProductTypeQueryModel<ProductType> queryModel, final ProductTypeExpansionModel<ProductType> expansionModel) {
        super(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<ProductType, ProductTypeQuery, ProductTypeQueryModel<ProductType>, ProductTypeExpansionModel<ProductType>> copyBuilder() {
        return new ProductTypeQueryQueryDslBuilder(this);
    }

    private static class ProductTypeQueryQueryDslBuilder extends UltraQueryDslBuilder<ProductType, ProductTypeQuery, ProductTypeQueryModel<ProductType>, ProductTypeExpansionModel<ProductType>> {
        public ProductTypeQueryQueryDslBuilder(final UltraQueryDslImpl<ProductType, ProductTypeQuery, ProductTypeQueryModel<ProductType>, ProductTypeExpansionModel<ProductType>> template) {
            super(template);
        }

        @Override
        public ProductTypeQueryImpl build() {
            return new ProductTypeQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}