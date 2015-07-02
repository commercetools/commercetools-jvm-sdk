package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.orders.OrderFixtures.withOrderAndReturnInfo;
import static org.assertj.core.api.Assertions.*;

public class MessageByIdFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            //query one message since message creation is not directly possible
            final MessageQuery query = MessageQuery.of()
                    .withPredicate(m -> m.type().is("ReturnInfoAdded"))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1);
            final Message messageFromQueryEndpoint = execute(query).head().get();

            final Message message = execute(MessageByIdFetch.of(messageFromQueryEndpoint)).get();
            assertThat(message).isEqualTo(messageFromQueryEndpoint);
        }));
    }
}