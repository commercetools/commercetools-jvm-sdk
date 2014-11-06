package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerName;
import io.sphere.sdk.customers.commands.updateactions.ChangeName;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.fest.assertions.Assertions.assertThat;

public class CustomerUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeName() throws Exception {
        withCustomer(client(), customer -> {
            final CustomerName newName = CustomerName.ofTitleFirstAndLastName("Mister", "John", "Smith");
            assertThat(customer.getName()).isNotEqualTo(newName);
            final Customer updatedCustomer = execute(new CustomerUpdateCommand(customer, ChangeName.of(newName)));
            assertThat(updatedCustomer.getName()).isEqualTo(newName);
        });
    }
}