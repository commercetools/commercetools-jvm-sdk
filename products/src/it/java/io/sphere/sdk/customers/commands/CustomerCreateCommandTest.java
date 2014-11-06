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

import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CustomerCreateCommandTest extends IntegrationTest {

    public static final CustomerName CUSTOMER_NAME = CustomerName.ofFirstAndLastName("John", "Smith");

    @Test
    public void createCustomer() throws Exception {
        final CustomerName name = CustomerName.ofFirstAndLastName("John", "Smith");
        final String email = randomEmail(CustomerCreateCommandTest.class);
        final String externalId = randomString();
        final String password = "secret";
        final CustomerDraft draft = CustomerDraft.of(name, email, password).withExternalId(externalId);
        final CustomerSignInResult result = execute(new CustomerCreateCommand(draft));
        final Customer customer = result.getCustomer();
        final Optional<Cart> cart = result.getCart();
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getPassword())
                .overridingErrorMessage("password is not stored in clear text")
                .isNotEqualTo(password);
        assertThat(customer.getExternalId()).isPresentAs(externalId);
        assertThat(cart).isAbsent();
    }

    @Test
    public void createCustomerWithCart() throws Exception {
        final Cart cart = execute(new CartCreateCommand(CartDraft.of(EUR)));
        final String email = randomEmail(CustomerCreateCommandTest.class);
        final CustomerDraft draft = CustomerDraft.of(CUSTOMER_NAME, email, "secret").withCart(cart);
        final CustomerSignInResult result = execute(new CustomerCreateCommand(draft));
        assertThat(result.getCart()).isPresent();
    }
}