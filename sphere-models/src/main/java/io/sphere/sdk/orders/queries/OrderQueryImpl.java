package io.sphere.sdk.orders.queries;

import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

final class OrderQueryImpl extends UltraQueryDslImpl<Order, OrderQuery, OrderQueryModel<Order>, OrderExpansionModel<Order>> implements OrderQuery {
    OrderQueryImpl(){
        super(OrderEndpoint.ENDPOINT.endpoint(), OrderQuery.resultTypeReference(), OrderQueryModel.of(), OrderExpansionModel.of());
    }

    private OrderQueryImpl(final Optional<QueryPredicate<Order>> predicate, final List<QuerySort<Order>> sort, final Optional<Long> limit, final Optional<Long> offset, final String endpoint, final Function<HttpResponse, PagedQueryResult<Order>> resultMapper, final List<ExpansionPath<Order>> expansionPaths, final List<HttpQueryParameter> additionalQueryParameters, final OrderQueryModel<Order> queryModel, final OrderExpansionModel<Order> expansionModel) {
        super(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
    }

    @Override
    protected UltraQueryDslBuilder<Order, OrderQuery, OrderQueryModel<Order>, OrderExpansionModel<Order>> copyBuilder() {
        return new OrderQueryQueryDslBuilder(this);
    }

    private static class OrderQueryQueryDslBuilder extends UltraQueryDslBuilder<Order, OrderQuery, OrderQueryModel<Order>, OrderExpansionModel<Order>> {
        public OrderQueryQueryDslBuilder(final UltraQueryDslImpl<Order, OrderQuery, OrderQueryModel<Order>, OrderExpansionModel<Order>> template) {
            super(template);
        }

        @Override
        public OrderQueryImpl build() {
            return new OrderQueryImpl(predicate, sort, limit, offset, endpoint, resultMapper, expansionPaths, additionalQueryParameters, queryModel, expansionModel);
        }
    }
}