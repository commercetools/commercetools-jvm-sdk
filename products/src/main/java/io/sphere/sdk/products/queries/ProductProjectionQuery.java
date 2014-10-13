package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

import java.util.Locale;

import static java.util.Arrays.asList;

/**
 {@doc.gen summary product projections}
 */
public class ProductProjectionQuery extends DefaultModelQuery<ProductProjection> {

    public ProductProjectionQuery(final ProductProjectionType productProjectionType) {
        super(ProductProjectionsEndpoint.ENDPOINT.endpoint(), asList(QueryParameter.of("staged", stagedQueryParameterValue(productProjectionType))), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<ProductProjection>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<ProductProjection>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<ProductProjection>>";
            }
        };
    }

    public static ProductProjectionQuery of(final ProductProjectionType productProjectionType) {
        return new ProductProjectionQuery(productProjectionType);
    }

    public QueryDsl<ProductProjection> byProductType(final Referenceable<ProductType> productType) {
        return withPredicate(model().productType().is(productType));
    }

    public static ProductProjectionQueryModel model() {
        return ProductProjectionQueryModel.get();
    }

    public Query<ProductProjection> bySlug(final Locale locale, final String slug) {
        return withPredicate(model().slug().lang(locale).is(slug));
    }

    public static ProductProjectionExpansionModel<ProductProjection> expansionPath() {
        return new ProductProjectionExpansionModel<>();
    }

    private static String stagedQueryParameterValue(final ProductProjectionType productProjectionType) {
        final boolean staged = productProjectionType == ProductProjectionType.STAGED;
        return "" + staged;
    }
}