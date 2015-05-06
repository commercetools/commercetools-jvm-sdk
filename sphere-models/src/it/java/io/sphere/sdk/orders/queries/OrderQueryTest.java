package io.sphere.sdk.orders.queries;

import io.sphere.sdk.carts.CartFixtures;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.UpdateSyncInfo;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.function.Function;

import static io.sphere.sdk.channels.ChannelFixtures.persistentChannelOfRole;
import static io.sphere.sdk.channels.ChannelRoles.ORDER_EXPORT;
import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class OrderQueryTest extends IntegrationTest {

    public static final OrderQueryModel MODEL = OrderQuery.model();

    @Test
    public void customerId() throws Exception {
        assertOrderIsFound(order -> {
            final String customerId = order.getCustomerId().get();
            return OrderQuery.of().byCustomerId(customerId);
        });
    }

    @Test
    public void customerEmail() throws Exception {
        assertOrderIsFound(order -> {
            final String customerEmail = order.getCustomerEmail().get();
            return OrderQuery.of().byCustomerEmail(customerEmail);
        });
    }

    @Test
    public void customerIdQueryModel() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.customerId().is(order.getCustomerId().get()));
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
        assertOrderIsFoundWithPredicate(order -> MODEL.shipmentState().is(order.getShipmentState().get()));
    }

    @Test
    public void paymentState() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.paymentState().is(order.getPaymentState().get()));
    }

    @Test
    public void syncInfo() throws Exception {
        final Channel channel = persistentChannelOfRole(client(), ORDER_EXPORT);
        assertOrderIsFoundWithPredicate(order -> {
            final String externalId = randomKey();
            execute(OrderUpdateCommand.of(order, UpdateSyncInfo.of(channel).withExternalId(externalId)));
            return MODEL.syncInfo().channel().is(channel).and(MODEL.syncInfo().externalId().is(externalId));
        });
    }

    private void assertOrderIsFound(final Function<Order, QueryDsl<Order>> p) {
        assertOrderIsFound(p, true);
    }

    private void assertOrderIsFound(final Function<Order, QueryDsl<Order>> p, final boolean shouldFind) {
        withOrder(client(), order -> {
            final QueryDsl<Order> query = p.apply(order).withSort(QuerySort.of("createdAt desc"));
            final String id = client().execute(query).head().orElseThrow(() -> new RuntimeException("nothing found with predicate")).getId();
            if (shouldFind) {
                assertThat(id).isEqualTo(order.getId());
            } else {
                assertThat(id).isNotEqualTo(order.getId());
            }
        });
    }

    private void assertOrderIsFoundWithPredicate(final Function<Order, Predicate<Order>> p) {
        assertOrderIsFound(order -> OrderQuery.of().withPredicate(p.apply(order)), true);
    }

    private void assertOrderIsNotFoundWithPredicate(final Function<Order, Predicate<Order>> p) {
        assertOrderIsFound(order -> OrderQuery.of().withPredicate(p.apply(order)), false);
    }
}