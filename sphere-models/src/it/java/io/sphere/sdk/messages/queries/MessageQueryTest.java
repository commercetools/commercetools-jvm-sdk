package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.messages.ReturnInfoAddedMessage;
import io.sphere.sdk.orders.messages.SimpleOrderMessage;
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
                    .withPredicate(m -> m.resource().id().is(order.getId()))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource());
            final List<Message> results = execute(query).getResults();

            final Message returnInfoAddedUntypedMessage = results.stream()
                    .filter(m -> m.getType().equals(ReturnInfoAddedMessage.MESSAGE_TYPE))
                    .findFirst().get();

            final ReturnInfoAddedMessage returnInfoAddedMessage =
                    returnInfoAddedUntypedMessage.as(ReturnInfoAddedMessage.class);

            assertThat(order.getReturnInfo()).contains(returnInfoAddedMessage.getReturnInfo());
            final Order expandedOrder = returnInfoAddedMessage.getResource().getObj().get();
            assertThat(expandedOrder.getCreatedAt()).isEqualTo(order.getCreatedAt());
        }));
    }

    @Test
    public void queryForASpecificResource() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Query<SimpleOrderMessage> query = MessageQuery.of()
                    .withPredicate(m -> m.resource().id().is(order.getId()))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .forMessageType(SimpleOrderMessage.MESSAGE_HINT);
            final List<SimpleOrderMessage> results = execute(query).getResults();

            final Optional<Order> orderOptional = results.get(0).getResource().getObj();
            assertThat(orderOptional.map(o -> o.getCreatedAt())).contains(order.getCreatedAt());
        }));
    }

    @Test
    public void queryForASpecificMessage() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Query<ReturnInfoAddedMessage> query =
                    MessageQuery.of()
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1)
                            .forMessageType(ReturnInfoAddedMessage.MESSAGE_HINT);
            final ReturnInfoAddedMessage message = execute(query).head().get();
            assertThat(message.getReturnInfo()).isEqualTo(returnInfo);
            assertThat(message.getResource().getObj()).isPresent();
            assertThat(message.getResource().getId()).isEqualTo(order.getId());
        }));
    }

    @Test
    public void queryForAllMessages() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final MessageQuery query = MessageQuery.of()
                    .withPredicate(m -> m.type().is(ReturnInfoAddedMessage.MESSAGE_TYPE))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1);
            final Message message = execute(query).head().get();
            assertThat(message.getResource().getObj()).isPresent();
            assertThat(message.getResource()).isEqualTo(order.toReference());
            assertThat(message.getResource().getId()).isEqualTo(order.getId());
        }));
    }
}