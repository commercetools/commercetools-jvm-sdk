package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.asList;

/**
 {@doc.gen summary product projections}
 */
final class ProductProjectionQueryImpl extends UltraQueryDslImpl<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel<ProductProjection>, ProductProjectionExpansionModel<ProductProjection>>
implements ProductProjectionQuery {

    ProductProjectionQueryImpl(final ProductProjectionType productProjectionType) {
        super(ProductProjectionsEndpoint.ENDPOINT.endpoint(), additionalParametersOf(productProjectionType), ProductProjectionQuery.resultTypeReference(), ProductProjectionQueryModel.of(), ProductProjectionExpansionModel.of());
    }

    private ProductProjectionQueryImpl(final Optional<QueryPredicate<ProductProjection>> productProjectionQueryPredicate, final List<QuerySort<ProductProjection>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<ProductProjection>> resultMapper, final List<ExpansionPath<ProductProjection>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final ProductProjectionQueryModel<ProductProjection> queryModel, final ProductProjectionExpansionModel<ProductProjection> expansionModel) {
        super(productProjectionQueryPredicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    private static List<HttpQueryParameter> additionalParametersOf(final ProductProjectionType productProjectionType) {
        return asList(HttpQueryParameter.of("staged", stagedQueryParameterValue(productProjectionType)));
    }

    public ProductProjectionQuery byProductType(final Referenceable<ProductType> productType) {
        return withPredicate(m -> m.productType().is(productType));
    }

    public ProductProjectionQuery bySlug(final Locale locale, final String slug) {
        return withPredicate(m -> m.slug().lang(locale).is(slug));
    }

    public static ProductProjectionQuery of(final ProductProjectionType productProjectionType) {
        return new ProductProjectionQueryImpl(productProjectionType);
    }

    private static String stagedQueryParameterValue(final ProductProjectionType productProjectionType) {
        final boolean staged = productProjectionType == ProductProjectionType.STAGED;
        return "" + staged;
    }

    @Override
    protected UltraQueryDslBuilder<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel<ProductProjection>, ProductProjectionExpansionModel<ProductProjection>> copyBuilder() {
        return new ProductProjectionQueryQueryDslBuilder(this);
    }

    private static class ProductProjectionQueryQueryDslBuilder extends UltraQueryDslBuilder<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel<ProductProjection>, ProductProjectionExpansionModel<ProductProjection>> {
        public ProductProjectionQueryQueryDslBuilder(final UltraQueryDslImpl<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel<ProductProjection>, ProductProjectionExpansionModel<ProductProjection>> template) {
            super(template);
        }

        @Override
        public ProductProjectionQueryImpl build() {
            return new ProductProjectionQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}