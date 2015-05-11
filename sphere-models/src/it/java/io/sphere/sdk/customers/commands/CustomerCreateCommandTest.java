package io.sphere.sdk.customers.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerName;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.customers.CustomerFixtures.*;

public class CustomerCreateCommandTest extends IntegrationTest {

    @Test
    public void createCustomer() throws Exception {
        final CustomerName name = CustomerName.ofFirstAndLastName("John", "Smith");
        final String email = randomEmail(CustomerCreateCommandTest.class);
        final String externalId = randomString();
        final String password = "secret";
        final CustomerDraft draft = CustomerDraft.of(name, email, password).withExternalId(externalId);
        final CustomerSignInResult result = execute(CustomerCreateCommand.of(draft));
        final Customer customer = result.getCustomer();
        final Optional<Cart> cart = result.getCart();
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getPassword())
                .overridingErrorMessage("password is not stored in clear text")
                .isNotEqualTo(password);
        assertThat(customer.getExternalId()).contains(externalId);
        assertThat(cart).isEmpty();
    }

    @Test
    public void createCustomerWithCart() throws Exception {
        final Cart cart = execute(CartCreateCommand.of(CartDraft.of(EUR)));
        final String email = randomEmail(CustomerCreateCommandTest.class);
        final CustomerDraft draft = CustomerDraft.of(CUSTOMER_NAME, email, PASSWORD).withCart(cart);
        final CustomerSignInResult result = execute(CustomerCreateCommand.of(draft));
        assertThat(result.getCart()).isPresent();
    }
}