package io.sphere.sdk.customers.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.queries.CartByCustomerIdGet;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerSignInResult;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.newCustomerDraft;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerDeleteCommandTest extends CustomerIntegrationTest {
    @Test
    public void execution() throws Exception {
        final CustomerSignInResult result = client().execute(CustomerCreateCommand.of(newCustomerDraft()));
        final Customer customer = result.getCustomer();
        execute(CustomerDeleteCommand.of(customer));
        final Cart cart = execute(CartByCustomerIdGet.of(customer));
        assertThat(cart).isNull();
    }
}