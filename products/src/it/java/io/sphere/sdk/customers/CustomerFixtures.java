package io.sphere.sdk.customers;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.customers.commands.CustomerCreateCommand;

import java.util.function.Consumer;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CustomerFixtures {
    public static final CustomerName CUSTOMER_NAME = CustomerName.ofFirstAndLastName("John", "Smith");
    public static final String PASSWORD = "secret";

    public static void withCustomer(final TestClient client, final Consumer<Customer> customerConsumer) {
        withCustomer(client, customerConsumer, CustomerDraft.of(CUSTOMER_NAME, randomEmail(CustomerFixtures.class), PASSWORD));
    }

    public static void withCustomer(final TestClient client,
                                    final Consumer<Customer> customerConsumer,
                                    final CustomerDraft draft) {
        final CustomerSignInResult signInResult = client.execute(new CustomerCreateCommand(draft));
        customerConsumer.accept(signInResult.getCustomer());
        //currently the backend does not allow customer deletion
    }
}
