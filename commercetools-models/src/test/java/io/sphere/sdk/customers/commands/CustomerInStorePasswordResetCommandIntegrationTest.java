package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.CustomerToken;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withUpdateableCustomerInStore;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStorePasswordResetCommandIntegrationTest extends CustomerIntegrationTest {

    @Test
    public void execution() throws Exception {
        withUpdateableCustomerInStore(client(), customer -> {
            final String storeKey = customer.getStores().get(0).getKey();
            final String email = customer.getEmail();
            final CustomerToken token = client().executeBlocking(CustomerInStoreCreatePasswordTokenCommand.of(storeKey, email));
            final String tokenValue = token.getValue();//this may need to be sent by email to the customer

            final String newPassword = "newPassword";
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerInStorePasswordResetCommand.ofTokenAndPassword(storeKey, tokenValue, newPassword));

            final CustomerSignInResult signInResult =
                    client().executeBlocking(CustomerInStoreSignInCommand.of(storeKey, email, newPassword));
            assertThat(signInResult.getCustomer().getId())
                    .describedAs("customer can sign in with the new password")
                    .isEqualTo(customer.getId());
            
            return updatedCustomer;
        });
    }
    
}
