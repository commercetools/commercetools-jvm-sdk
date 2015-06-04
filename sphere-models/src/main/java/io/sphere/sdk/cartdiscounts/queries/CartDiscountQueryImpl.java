package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**

 {@doc.gen summary cart discounts}

 */
final class CartDiscountQueryImpl extends UltraQueryDslImpl<CartDiscount, CartDiscountQuery, CartDiscountQueryModel<CartDiscount>, CartDiscountExpansionModel<CartDiscount>> implements CartDiscountQuery {
    CartDiscountQueryImpl(){
        super(CartDiscountEndpoint.ENDPOINT.endpoint(), CartDiscountQuery.resultTypeReference(), CartDiscountQueryModel.of(), CartDiscountExpansionModel.of());
    }

    private CartDiscountQueryImpl(final Optional<QueryPredicate<CartDiscount>> productProjectionQueryPredicate, final List<QuerySort<CartDiscount>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<CartDiscount>> resultMapper, final List<ExpansionPath<CartDiscount>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final CartDiscountQueryModel<CartDiscount> queryModel, final CartDiscountExpansionModel<CartDiscount> expansionModel) {
        super(productProjectionQueryPredicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<CartDiscount, CartDiscountQuery, CartDiscountQueryModel<CartDiscount>, CartDiscountExpansionModel<CartDiscount>> copyBuilder() {
        return new CartDiscountQueryQueryDslBuilder(this);
    }

    private static class CartDiscountQueryQueryDslBuilder extends UltraQueryDslBuilder<CartDiscount, CartDiscountQuery, CartDiscountQueryModel<CartDiscount>, CartDiscountExpansionModel<CartDiscount>> {
        public CartDiscountQueryQueryDslBuilder(final UltraQueryDslImpl<CartDiscount, CartDiscountQuery, CartDiscountQueryModel<CartDiscount>, CartDiscountExpansionModel<CartDiscount>> template) {
            super(template);
        }

        @Override
        public CartDiscountQueryImpl build() {
            return new CartDiscountQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}
