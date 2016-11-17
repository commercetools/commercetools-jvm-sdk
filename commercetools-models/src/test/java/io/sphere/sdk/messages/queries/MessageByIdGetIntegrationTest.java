package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.Duration;
import java.util.Optional;

import static io.sphere.sdk.orders.OrderFixtures.withOrderAndReturnInfo;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.*;

public class MessageByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            //query one message since message creation is not directly possible
            final MessageQuery query = MessageQuery.of()
                    .withPredicates(m -> m.type().is("ReturnInfoAdded"))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L);
            assertEventually(Duration.ofSeconds(45), Duration.ofMillis(100), () -> {
                final Optional<Message> messageOptional = client().executeBlocking(query).head();
                assertThat(messageOptional).isPresent();
                final Message messageFromQueryEndpoint = messageOptional.get();
                final Message message = client().executeBlocking(MessageByIdGet.of(messageFromQueryEndpoint));
                assertThat(message).isEqualTo(messageFromQueryEndpoint);
            });

            return order;
        }));
    }
}