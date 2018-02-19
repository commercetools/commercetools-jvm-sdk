package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.Command;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.messages.CustomerEmailVerifiedMessage;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerCreateEmailTokenCommandIntegrationTest extends CustomerIntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.isEmailVerified()).isFalse();
            final int ttlMinutes = 15;
            final Command<CustomerToken> createTokenCommand =
                    CustomerCreateEmailTokenCommand.ofCustomerId(customer.getId(), ttlMinutes);

            final CustomerToken customerToken = client().executeBlocking(createTokenCommand);
            final String tokenValue = customerToken.getValue();//this token needs to be sent via email to the customer

            final Command<Customer> verifyEmailCommand = CustomerVerifyEmailCommand.ofTokenValue(tokenValue);
            final Customer loadedCustomer = client().executeBlocking(verifyEmailCommand);
            assertThat(loadedCustomer.isEmailVerified()).isTrue();

            Query<CustomerEmailVerifiedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(customer))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .forMessageType(CustomerEmailVerifiedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<CustomerEmailVerifiedMessage> queryResult = client().executeBlocking(messageQuery);
                assertThat(queryResult.head()).isPresent();
            });

        });
    }
}