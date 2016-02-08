package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.messages.DeliveryAddedMessage;
import io.sphere.sdk.orders.messages.ReturnInfoAddedMessage;
import io.sphere.sdk.orders.messages.SimpleOrderMessage;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.orders.OrderFixtures.withOrderAndReturnInfo;
import static org.assertj.core.api.Assertions.*;

public class MessageQueryTest extends IntegrationTest {
    @Test
    public void convertAfterQueryToSpecificMessageClasses() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final MessageQuery query = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(order))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource());
            final List<Message> results = client().executeBlocking(query).getResults();

            final Message returnInfoAddedUntypedMessage = results.stream()
                    .filter(m -> {
                        final String messageType = ReturnInfoAddedMessage.MESSAGE_TYPE;
                        return m.getType().equals(messageType);
                    })
                    .findFirst().get();

            final ReturnInfoAddedMessage returnInfoAddedMessage =
                    returnInfoAddedUntypedMessage.as(ReturnInfoAddedMessage.class);

            assertThat(order.getReturnInfo()).contains(returnInfoAddedMessage.getReturnInfo());
            final Order expandedOrder = returnInfoAddedMessage.getResource().getObj();
            assertThat(expandedOrder.getCreatedAt()).isEqualTo(order.getCreatedAt());

            return order;
        }));
    }

    @Test
    public void convertAfterQueryToSpecificMessageClassesButToTheWrongOne() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final MessageQuery query = MessageQuery.of()
                    .withPredicates(m -> m.resource().id().is(order.getId()))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource());
            final List<Message> results = client().executeBlocking(query).getResults();

            final Message returnInfoAddedUntypedMessage = results.stream()
                    .filter(m -> {
                        final String messageType = ReturnInfoAddedMessage.MESSAGE_TYPE;
                        return m.getType().equals(messageType);
                    })
                    .findFirst().get();


            final DeliveryAddedMessage deliveryAddedMessage
                    //wrong cast, but may not explodes
                    = returnInfoAddedUntypedMessage.as(DeliveryAddedMessage.class);
            assertThat(deliveryAddedMessage.getDelivery())
                    .overridingErrorMessage("with wrong cast, fields can be null")
                    .isNull();

            return order;
        }));
    }

    @Test
    public void queryForASpecificResource() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Query<SimpleOrderMessage> query = MessageQuery.of()
                    .withPredicates(m -> m.resource().id().is(order.getId()))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .forMessageType(SimpleOrderMessage.MESSAGE_HINT);
            final List<SimpleOrderMessage> results = client().executeBlocking(query).getResults();

            final Optional<Order> orderOptional = Optional.ofNullable(results.get(0).getResource().getObj());
            assertThat(orderOptional.map(o -> o.getCreatedAt())).contains(order.getCreatedAt());

            return order;
        }));
    }

    @Test
    public void queryForASpecificMessage() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Query<ReturnInfoAddedMessage> query =
                    MessageQuery.of()
                            .withPredicates(m -> m.resource().is(order))
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1L)
                            .forMessageType(ReturnInfoAddedMessage.MESSAGE_HINT);
            final PagedQueryResult<ReturnInfoAddedMessage> pagedQueryResult = client().executeBlocking(query);
            final ReturnInfoAddedMessage message = pagedQueryResult.head().get();
            assertThat(message.getReturnInfo()).isEqualTo(returnInfo);
            assertThat(message.getResource().getObj()).isNotNull();
            assertThat(message.getResource().getId()).isEqualTo(order.getId());

            return order;
        }));
    }

    @Test
    public void messageGetPayload() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Query<Message> query =
                    MessageQuery.of()
                            .withPredicates(m -> m.type().is(ReturnInfoAddedMessage.MESSAGE_HINT).and(m.resource().is(order)))
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1L);
            final PagedQueryResult<Message> pagedQueryResult = client().executeBlocking(query);
            final Message message = pagedQueryResult.head().get();

            final String fetchedItemId = message.getPayload().get("returnInfo").get("items").get(0).get("id").asText();
            final String actualItemId = returnInfo.getItems().get(0).getId();
            assertThat(fetchedItemId).isEqualTo(actualItemId).isNotNull();

            return order;
        }));
    }

    @Test
    public void queryForAllMessages() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final MessageQuery query = MessageQuery.of()
                    //example predicate to fetch for a specific message type
                    .withPredicates(m -> m.type().is("ReturnInfoAdded").and(m.resource().is(order)))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1L);
            final Message message = client().executeBlocking(query).head().get();
            assertThat(message.getResource().getObj()).isNotNull();
            assertThat(message.getResource()).isEqualTo(order.toReference());
            assertThat(message.getResource().getId()).isEqualTo(order.getId());

            return order;
        }));
    }
}