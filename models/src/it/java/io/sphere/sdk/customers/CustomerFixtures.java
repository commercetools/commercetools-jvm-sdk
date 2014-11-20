package io.sphere.sdk.customers;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.customers.commands.CustomerCreateCommand;
import io.sphere.sdk.customers.commands.CustomerUpdateCommand;
import io.sphere.sdk.customers.commands.updateactions.AddAddress;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class CustomerFixtures {
    public static final CustomerName CUSTOMER_NAME = CustomerName.ofFirstAndLastName("John", "Smith");
    public static final String PASSWORD = "secret";

    public static void withCustomerWithOneAddress(final TestClient client, final Consumer<Customer> customerConsumer) {
        final Consumer<Customer> customerUpdater = customer -> {
            final Address address = AddressBuilder.of(DE).city("address city").build();
            final Customer customerWithAddress = client.execute(new CustomerUpdateCommand(customer, AddAddress.of(address)));
            assertThat(customerWithAddress.getAddresses()).hasSize(1);
            customerConsumer.accept(customerWithAddress);
        };
        withCustomer(client, newCustomerDraft(), customerUpdater);
    }

    public static void withCustomer(final TestClient client, final Consumer<Customer> customerConsumer) {
        withCustomer(client, newCustomerDraft(), customerConsumer);
    }

    public static void withCustomer(final TestClient client,
                                    final CustomerDraft draft, final Consumer<Customer> customerConsumer) {
        final CustomerSignInResult signInResult = client.execute(new CustomerCreateCommand(draft));
        customerConsumer.accept(signInResult.getCustomer());
        //currently the backend does not allow customer deletion
    }

    public static void withCustomerAndCart(final TestClient client, final BiConsumer<Customer, Cart> consumer) {
        withCustomer(client, customer -> {
            final CartDraft cartDraft = CartDraft.of(EUR).withCustomerId(customer.getId());
            final Cart cart = client.execute(new CartCreateCommand(cartDraft));
            consumer.accept(customer, cart);
        });
    }

    public static CustomerDraft newCustomerDraft() {
        return CustomerDraft.of(CUSTOMER_NAME, randomEmail(CustomerFixtures.class), PASSWORD);
    }
}
