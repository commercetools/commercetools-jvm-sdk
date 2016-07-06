package io.sphere.sdk.orders.messages;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.ChangeOrderState;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.orders.OrderFixtures.withOrderAndReturnInfo;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderMessagesIntegrationTest extends IntegrationTest {
    @Test
    public void orderStateChangedMessage() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, ChangeOrderState.of(OrderState.CANCELLED)));
            final Query<OrderStateChangedMessage> query =
                    MessageQuery.of()
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1L)
                            .forMessageType(OrderStateChangedMessage.MESSAGE_HINT);
            assertEventually(() -> {
                final PagedQueryResult<OrderStateChangedMessage> pagedQueryResult = client().executeBlocking(query);
                final Optional<OrderStateChangedMessage> message = pagedQueryResult.head();

                assertThat(message).isPresent();
                assertThat(message.get().getOrderState()).isEqualTo(OrderState.CANCELLED);
                assertThat(message.get().getResource().getObj()).isNotNull();
                assertThat(message.get().getResource().getId()).isEqualTo(order.getId());
            });

            return updatedOrder;
        }));
    }

    @Test
    public void orderCreatedMessage() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Query<OrderCreatedMessage> query =
                    MessageQuery.of()
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1L)
                            .withPredicates(m -> m.resource().is(order))
                            .forMessageType(OrderCreatedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<OrderCreatedMessage> pagedQueryResult = client().executeBlocking(query);
                final Optional<OrderCreatedMessage> message = pagedQueryResult.head();

                assertThat(message).isPresent();
                assertThat(message.get().getOrder().getId()).isEqualTo(order.getId());
                assertThat(message.get().getResource().getObj()).isNotNull();
                assertThat(message.get().getResource().getId()).isEqualTo(order.getId());
            });

            return order;
        }));
    }
}
