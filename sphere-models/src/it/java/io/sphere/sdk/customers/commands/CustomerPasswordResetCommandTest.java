package io.sphere.sdk.customers.commands;

import io.sphere.sdk.client.ClientErrorException;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.queries.CustomerByPasswordTokenGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CustomerPasswordResetCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final String email = customer.getEmail();
            final CustomerToken token = execute(CustomerCreatePasswordTokenCommand.of(email));
            final String tokenValue = token.getValue();//this may need to be sent by email to the customer

            final Customer customerByToken = execute(CustomerByPasswordTokenGet.of(tokenValue));
            final String newPassword = "newPassword";
            final Customer updatedCustomer =
                    execute(CustomerPasswordResetCommand.of(customerByToken, tokenValue, newPassword));

            final CustomerSignInResult signInResult = execute(CustomerSignInCommand.of(email, newPassword));
            assertThat(signInResult.getCustomer().getId())
                    .describedAs("customer can sign in with the new password")
                    .isEqualTo(customer.getId());
        });
    }

    @Test
    public void outdatedOrWrongToken() throws Exception {
        withCustomer(client(), customer -> {
            final String tokenValue = "wrong-token-value";

            final Throwable throwable = catchThrowable(() ->
                    execute(CustomerPasswordResetCommand.of(customer, tokenValue, "newPassword")));

            assertThat(throwable).isInstanceOf(ClientErrorException.class);
        });
    }
}