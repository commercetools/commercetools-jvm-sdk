package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.orders.messages.ReturnInfoAddedMessage;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.orders.OrderFixtures.withOrderAndReturnInfo;
import static org.assertj.core.api.Assertions.*;

public class MessageQueryTest extends IntegrationTest {
    @Test
    public void queryForASpecificMessage() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Query<ReturnInfoAddedMessage> query =
                    MessageQuery.of()
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1)
                            .forMessageType(ReturnInfoAddedMessage.MESSAGE_TYPE);
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
                    .withPredicate(m -> m.type().is(ReturnInfoAddedMessage.MESSAGE_TYPE.type()))
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