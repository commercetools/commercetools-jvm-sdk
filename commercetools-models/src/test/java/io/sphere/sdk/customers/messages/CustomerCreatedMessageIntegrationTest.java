package io.sphere.sdk.customers.messages;

import io.sphere.sdk.customers.CustomerDraft;
import org.junit.Test;

import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;

import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.customers.CustomerFixtures.*;

public class CustomerCreatedMessageIntegrationTest extends IntegrationTest {
    @Test
    public void customerCreatedMessage() {
        final String customerNumber = randomString();
        final CustomerDraft customerDraft = newCustomerDraft().withCustomerNumber(customerNumber);

        CustomerFixtures.withCustomer(client(),customerDraft, customer -> {
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
                assertThat(message.getResourceUserProvidedIdentifiers().getCustomerNumber()).isEqualTo(customerNumber);
            });
        });
    }
}
