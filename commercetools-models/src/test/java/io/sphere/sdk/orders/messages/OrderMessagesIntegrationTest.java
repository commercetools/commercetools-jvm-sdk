package io.sphere.sdk.orders.messages;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.OrderImportCommand;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.ChangeOrderState;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.util.Optional;

import static io.sphere.sdk.orders.OrderFixtures.withOrderAndReturnInfo;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderMessagesIntegrationTest extends IntegrationTest {
    @Test
    public void orderStateChangedMessage() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, ChangeOrderState.of(OrderState.CANCELLED)));
            final Query<OrderStateChangedMessage> query =
                    MessageQuery.of()
                            .withPredicates(m -> m.resource().id().is(updatedOrder.getId()))
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
                            .withPredicates(m -> m.resource().is(order))
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1L)
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

    @Test
    public void orderImportedMessage() throws Exception {
        withProduct(client(), product -> {
            final String productId = product.getId();
            final int variantId = 1;
            final LocalizedString name = en("a name");
            final long quantity = 1;
            final Price price = Price.of(EURO_10);
            final OrderState orderState = OrderState.OPEN;
            final MonetaryAmount amount = EURO_10;
            final ProductVariantImportDraft variant = ProductVariantImportDraftBuilder.of(productId, variantId).build();

            final LineItemImportDraft lineItemImportDraft = LineItemImportDraftBuilder.of(variant, quantity, price, name).build();
            final OrderImportDraft orderImportDraft = OrderImportDraftBuilder.ofLineItems(amount, orderState, asList(lineItemImportDraft))
                    .country(DE).build();
            final OrderImportCommand cmd = OrderImportCommand.of(orderImportDraft);

            final Order order = client().executeBlocking(cmd);

            //you can observe a message
            final Query<OrderImportedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(order))
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1L)
                    .forMessageType(OrderImportedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final Optional<OrderImportedMessage> orderImportedMessageOptional = client().executeBlocking(messageQuery).head();
                assertThat(orderImportedMessageOptional).isPresent();
                final OrderImportedMessage orderImportedMessage = orderImportedMessageOptional.get();
                final Order orderFromMessage = orderImportedMessage.getOrder();
                assertThat(orderFromMessage.getId()).isEqualTo(order.getId());
                final Reference<Order> resource = orderImportedMessage.getResource();
                assertThat(resource.getObj()).isNotNull();
                assertThat(resource.getId()).isEqualTo(order.getId());

            });

        });
    }
}
