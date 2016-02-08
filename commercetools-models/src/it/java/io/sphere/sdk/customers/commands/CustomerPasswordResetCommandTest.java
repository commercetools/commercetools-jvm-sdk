package io.sphere.sdk.customers.commands;

import io.sphere.sdk.client.ClientErrorException;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.queries.CustomerByPasswordTokenGet;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CustomerPasswordResetCommandTest extends CustomerIntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final String email = customer.getEmail();
            final CustomerToken token = client().executeBlocking(CustomerCreatePasswordTokenCommand.of(email));
            final String tokenValue = token.getValue();//this may need to be sent by email to the customer

            final Customer customerByToken = client().executeBlocking(CustomerByPasswordTokenGet.of(tokenValue));
            final String newPassword = "newPassword";
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerPasswordResetCommand.of(customerByToken, tokenValue, newPassword));

            final CustomerSignInResult signInResult = client().executeBlocking(CustomerSignInCommand.of(email, newPassword));
            assertThat(signInResult.getCustomer().getId())
                    .describedAs("customer can sign in with the new password")
                    .isEqualTo(customer.getId());
        });
    }

    @Test
    public void outdatedOrWrongToken() throws Exception {
        withCustomer(client(), customer -> {
            final String tokenValue = "wrong-token-value";

            final Throwable throwable = catchThrowable(() -> client().executeBlocking(CustomerPasswordResetCommand.of(customer, tokenValue, "newPassword")));

            assertThat(throwable).isInstanceOf(ClientErrorException.class);
        });
    }
}