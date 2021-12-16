package io.sphere.sdk.customers.messages;

import io.sphere.sdk.customers.*;
import io.sphere.sdk.customers.commands.CustomerDeleteCommand;
import io.sphere.sdk.customers.commands.CustomerUpdateCommand;
import io.sphere.sdk.customers.commands.updateactions.ChangeName;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerDeletedMessageIntegrationTest extends IntegrationTest {
    @Test
    public void customerDeletedMessage() {
        withCustomer(client(), customer -> {
            final CustomerName newName = CustomerName.ofTitleFirstAndLastName("Mister", "John", "Smith");
            final Customer customerWithNewName = client().executeBlocking(CustomerUpdateCommand.of(customer, ChangeName.of(newName)));
            final Customer deletedCustomer = client().executeBlocking(CustomerDeleteCommand.of(customerWithNewName));

            final Query<CustomerDeletedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(deletedCustomer))
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1L)
                    .forMessageType(CustomerDeletedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final Optional<CustomerDeletedMessage> customerDeletedMessageOptional = client().executeBlocking(messageQuery).head();
                assertThat(customerDeletedMessageOptional).isPresent();

                final CustomerDeletedMessage customerDeletedMessage = customerDeletedMessageOptional.get();

                assertThat(customerDeletedMessage.getType()).contains(CustomerDeletedMessage.MESSAGE_TYPE);
            });
        });
    }
}
