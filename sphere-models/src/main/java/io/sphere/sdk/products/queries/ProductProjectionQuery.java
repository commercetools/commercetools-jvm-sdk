package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
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
public final class ProductProjectionQuery extends UltraQueryDslImpl<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> {

    private ProductProjectionQuery(final ProductProjectionType productProjectionType) {
        super(ProductProjectionsEndpoint.ENDPOINT.endpoint(), additionalParametersOf(productProjectionType), resultTypeReference(), ProductProjectionQueryModel.get(), ProductProjectionQuery.expansionPath());
    }

    private ProductProjectionQuery(final Optional<QueryPredicate<ProductProjection>> productProjectionQueryPredicate, final List<QuerySort<ProductProjection>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<ProductProjection>> resultMapper, final List<ExpansionPath<ProductProjection>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final ProductProjectionQueryModel queryModel, final ProductProjectionExpansionModel<ProductProjection> expansionModel) {
        super(productProjectionQueryPredicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    private static List<HttpQueryParameter> additionalParametersOf(final ProductProjectionType productProjectionType) {
        return asList(HttpQueryParameter.of("staged", stagedQueryParameterValue(productProjectionType)));
    }

    public static TypeReference<PagedQueryResult<ProductProjection>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ProductProjection>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductProjection>>";
            }
        };
    }

    public ProductProjectionQuery byProductType(final Referenceable<ProductType> productType) {
        return withPredicate(model().productType().is(productType));
    }

    public ProductProjectionQuery bySlug(final Locale locale, final String slug) {
        return withPredicate(model().slug().lang(locale).is(slug));
    }

    public static ProductProjectionQuery ofStaged() {
        return of(ProductProjectionType.STAGED);
    }

    public static ProductProjectionQuery ofCurrent() {
        return of(ProductProjectionType.CURRENT);
    }

    public static ProductProjectionQuery of(final ProductProjectionType productProjectionType) {
        return new ProductProjectionQuery(productProjectionType);
    }

    public static ProductProjectionQueryModel model() {
        return ProductProjectionQueryModel.get();
    }

    public static ProductProjectionExpansionModel<ProductProjection> expansionPath() {
        return new ProductProjectionExpansionModel<>();
    }

    private static String stagedQueryParameterValue(final ProductProjectionType productProjectionType) {
        final boolean staged = productProjectionType == ProductProjectionType.STAGED;
        return "" + staged;
    }

    @Override
    protected UltraQueryDslBuilder<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> copyBuilder() {
        return new ProductProjectionQueryQueryDslBuilder(this);
    }

    private static class ProductProjectionQueryQueryDslBuilder extends UltraQueryDslBuilder<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> {
        public ProductProjectionQueryQueryDslBuilder(final UltraQueryDslImpl<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> template) {
            super(template);
        }

        @Override
        public ProductProjectionQuery build() {
            return new ProductProjectionQuery(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}