package io.sphere.sdk.customers.messages;

import org.junit.Test;

import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;

import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerCreatedMessageIntegrationTest extends IntegrationTest {
    @Test
    public void customerCreatedMessage() {
        CustomerFixtures.withCustomer(client(), customer -> {
            Query<CustomerCreatedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(customer))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .forMessageType(CustomerCreatedMessage.MESSAGE_HINT);
            
            assertEventually(() -> {
                final PagedQueryResult<CustomerCreatedMessage> queryResult = client().executeBlocking(messageQuery);
                assertThat(queryResult.head()).isPresent();
                final CustomerCreatedMessage message = queryResult.head().get();
                assertThat(message.getCustomer()).isEqualTo(customer);
            });
        });
    }
}
