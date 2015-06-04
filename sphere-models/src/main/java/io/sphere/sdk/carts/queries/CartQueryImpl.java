package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**

 {@doc.gen summary carts}

 */
final class CartQueryImpl extends UltraQueryDslImpl<Cart, CartQuery, CartQueryModel<Cart>, CartExpansionModel<Cart>> implements CartQuery {
    CartQueryImpl(){
        super(CartEndpoint.ENDPOINT.endpoint(), CartQuery.resultTypeReference(), CartQueryModel.of(), CartExpansionModel.of());
    }

    private CartQueryImpl(final Optional<QueryPredicate<Cart>> predicate, final List<QuerySort<Cart>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<Cart>> resultMapper, final List<ExpansionPath<Cart>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final CartQueryModel<Cart> queryModel, final CartExpansionModel<Cart> expansionModel) {
        super(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<Cart, CartQuery, CartQueryModel<Cart>, CartExpansionModel<Cart>> copyBuilder() {
        return new CartQueryQueryDslBuilder(this);
    }

    private static class CartQueryQueryDslBuilder extends UltraQueryDslBuilder<Cart, CartQuery, CartQueryModel<Cart>, CartExpansionModel<Cart>> {
        public CartQueryQueryDslBuilder(final UltraQueryDslImpl<Cart, CartQuery, CartQueryModel<Cart>, CartExpansionModel<Cart>> template) {
            super(template);
        }

        @Override
        public CartQueryImpl build() {
            return new CartQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}