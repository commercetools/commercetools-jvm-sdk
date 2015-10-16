package io.sphere.sdk.orders.queries;

import io.sphere.sdk.carts.CartFixtures;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.UpdateSyncInfo;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.channels.ChannelFixtures.persistentChannelOfRole;
import static io.sphere.sdk.channels.ChannelRole.ORDER_EXPORT;
import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderQueryTest extends IntegrationTest {

    public static final OrderQueryModel MODEL = OrderQueryModel.of();

    @Test
    public void customerGroupIsExpandeable() throws Exception {
        CustomerFixtures.withCustomerInGroup(client(), (customer, customerGroup) -> {
            withOrder(client(), customer, order -> {
                final Order queriedOrder = execute(OrderQuery.of()
                                .withPredicates(m -> m.id().is(order.getId()).and(m.customerGroup().is(customerGroup)))
                                .withExpansionPaths(m -> m.customerGroup())
                ).head().get();
                assertThat(queriedOrder.getCustomerGroup().getObj().getName())
                        .overridingErrorMessage("customerGroupIsExpandeable")
                        .isEqualTo(customerGroup.getName());
                return order;
            });
        });
    }

    @Test
    public void customerId() throws Exception {
        assertOrderIsFound(order -> {
            final String customerId = order.getCustomerId();
            return OrderQuery.of().byCustomerId(customerId);
        });
    }

    @Test
    public void customerEmail() throws Exception {
        assertOrderIsFound(order -> {
            final String customerEmail = order.getCustomerEmail();
            return OrderQuery.of().byCustomerEmail(customerEmail);
        });
    }

    @Test
    public void customerIdQueryModel() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.customerId().is(order.getCustomerId()));
    }

    @Test
    public void customerEmailQueryModel() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.customerEmail().is(OrderFixtures.CUSTOMER_EMAIL));
    }

    @Test
    public void country() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.country().is(CartFixtures.DEFAULT_COUNTRY));
    }

    @Test
    public void orderState() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.orderState().is(order.getOrderState()));
    }

    @Test
    public void shipmentState() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.shipmentState().is(order.getShipmentState()));
    }

    @Test
    public void paymentState() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.paymentState().is(order.getPaymentState()));
    }

    @Test
    public void cart() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.cart().is(order.getCart()));
    }

    @Test
    public void syncInfo() throws Exception {
        final Channel channel = persistentChannelOfRole(client(), ORDER_EXPORT);
        final String externalId = randomKey();
        assertOrderIsFoundWithPredicate(
                order -> execute(OrderUpdateCommand.of(order, UpdateSyncInfo.of(channel).withExternalId(externalId))),
                order -> MODEL.syncInfo().channel().is(channel).and(MODEL.syncInfo().externalId().is(externalId)));
    }

    private void assertOrderIsFound(final Function<Order, OrderQuery> p) {
        assertOrderIsFound(p, true);
    }

    private void assertOrderIsFound(final Function<Order, OrderQuery> predicateCreator, final boolean shouldFind) {
        assertOrderIsFound(x -> x, predicateCreator, shouldFind);
    }

    private void assertOrderIsFound(final UnaryOperator<Order> orderMutator, final Function<Order, OrderQuery> p, final boolean shouldFind) {
        withOrder(client(), order -> {
            final Order updatedOrder = orderMutator.apply(order);
            assertEventually(() -> {

                final OrderQuery query = p.apply(order).withSort(QuerySort.of("createdAt desc"));
                final List<Order> results = client().execute(query).getResults();

                if (shouldFind) {
                    assertThat(results).extracting("id").contains(order.getId());
                } else {
                    assertThat(results).extracting("id").doesNotContain(order.getId());
                }
            });
            return updatedOrder;
        });
    }

    private void assertOrderIsFoundWithPredicate(final UnaryOperator<Order> orderMutator, final Function<Order, QueryPredicate<Order>> p) {
        assertOrderIsFound(orderMutator, order -> OrderQuery.of().withPredicates(p.apply(order)), true);
    }

    private void assertOrderIsFoundWithPredicate(final Function<Order, QueryPredicate<Order>> p) {
        assertOrderIsFound(order -> OrderQuery.of().withPredicates(p.apply(order)), true);
    }

    private void assertOrderIsNotFoundWithPredicate(final Function<Order, QueryPredicate<Order>> p) {
        assertOrderIsFound(order -> OrderQuery.of().withPredicates(p.apply(order)), false);
    }
}