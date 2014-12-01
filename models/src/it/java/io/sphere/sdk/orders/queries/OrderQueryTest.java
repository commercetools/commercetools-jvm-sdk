package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.Sort;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.function.Function;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static org.fest.assertions.Assertions.assertThat;

public class OrderQueryTest extends IntegrationTest {

    public static final OrderQueryModel MODEL = OrderQuery.model();

    @Test
    public void customerId() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.customerId().is(order.getCustomerId().get()));
    }

    @Test
    public void customerEmail() throws Exception {
        assertOrderIsFoundWithPredicate(order -> MODEL.customerEmail().is(OrderFixtures.CUSTOMER_EMAIL));

    }

    private void assertOrderIsFoundWithPredicate(final Function<Order, Predicate<Order>> p) {
        withOrder(client(), order -> {
            final Predicate<Order> predicate = p.apply(order);
            final Query<Order> query = OrderQuery.of().withPredicate(predicate).withSort(Sort.of("createdAt desc"));
            assertThat(client().execute(query).head().get().getId()).isEqualTo(order.getId());
        });
    }
}