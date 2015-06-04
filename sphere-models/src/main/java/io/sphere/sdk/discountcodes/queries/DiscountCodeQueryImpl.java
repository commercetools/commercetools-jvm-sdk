package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DiscountCodeQueryImpl extends UltraQueryDslImpl<DiscountCode, DiscountCodeQuery, DiscountCodeQueryModel<DiscountCode>, DiscountCodeExpansionModel<DiscountCode>> implements DiscountCodeQuery {
    DiscountCodeQueryImpl(){
        super(DiscountCodeEndpoint.ENDPOINT.endpoint(), DiscountCodeQuery.resultTypeReference(), DiscountCodeQueryModel.of(), DiscountCodeExpansionModel.of());
    }

    private DiscountCodeQueryImpl(final Optional<QueryPredicate<DiscountCode>> predicate, final List<QuerySort<DiscountCode>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<DiscountCode>> resultMapper, final List<ExpansionPath<DiscountCode>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final DiscountCodeQueryModel<DiscountCode> queryModel, final DiscountCodeExpansionModel<DiscountCode> expansionModel) {
        super(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<DiscountCode, DiscountCodeQuery, DiscountCodeQueryModel<DiscountCode>, DiscountCodeExpansionModel<DiscountCode>> copyBuilder() {
        return new DiscountCodeQueryQueryDslBuilder(this);
    }

    private static class DiscountCodeQueryQueryDslBuilder extends UltraQueryDslBuilder<DiscountCode, DiscountCodeQuery, DiscountCodeQueryModel<DiscountCode>, DiscountCodeExpansionModel<DiscountCode>> {
        public DiscountCodeQueryQueryDslBuilder(final UltraQueryDslImpl<DiscountCode, DiscountCodeQuery, DiscountCodeQueryModel<DiscountCode>, DiscountCodeExpansionModel<DiscountCode>> template) {
            super(template);
        }

        @Override
        public DiscountCodeQueryImpl build() {
            return new DiscountCodeQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}