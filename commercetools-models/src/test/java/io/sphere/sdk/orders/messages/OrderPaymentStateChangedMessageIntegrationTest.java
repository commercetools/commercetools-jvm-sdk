package io.sphere.sdk.orders.messages;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.PaymentState;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.ChangePaymentState;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.orders.OrderFixtures.withNonUpdatedOrder;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderPaymentStateChangedMessageIntegrationTest extends IntegrationTest {

    @Test
    public void changePaymentState() throws Exception {

        withNonUpdatedOrder(client(), order -> {
            final PaymentState newState = PaymentState.PAID;
            assertThat(order.getPaymentState()).isNotEqualTo(newState);
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, ChangePaymentState.of(newState)));
            assertThat(updatedOrder.getPaymentState()).isEqualTo(newState);

            final Query<OrderPaymentStateChangedMessage> query =
                    MessageQuery.of()
                            .withPredicates(m -> m.resource().id().is(updatedOrder.getId()))
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1L)
                            .forMessageType(OrderPaymentStateChangedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<OrderPaymentStateChangedMessage> pagedQueryResult = client().executeBlocking(query);
                final Optional<OrderPaymentStateChangedMessage> optMessage = pagedQueryResult.head();
                assertThat(optMessage).isPresent();
                OrderPaymentStateChangedMessage message = optMessage.get();
                assertThat(message.getResource().getId()).isEqualTo(updatedOrder.getId());
                assertThat(message.getPaymentState()).isEqualTo(newState);
            });

            return updatedOrder;
        });
    }
}
