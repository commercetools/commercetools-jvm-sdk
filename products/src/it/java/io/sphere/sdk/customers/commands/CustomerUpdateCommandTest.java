package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerName;
import io.sphere.sdk.customers.commands.updateactions.AddAddress;
import io.sphere.sdk.customers.commands.updateactions.ChangeEmail;
import io.sphere.sdk.customers.commands.updateactions.ChangeName;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Predicate;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;

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

    @Test
    public void changeEmail() throws Exception {
        withCustomer(client(), customer -> {
            final String newEmail = randomEmail(CustomerUpdateCommandTest.class);
            assertThat(customer.getEmail()).isNotEqualTo(newEmail);
            final Customer updatedCustomer = execute(new CustomerUpdateCommand(customer, ChangeEmail.of(newEmail)));
            assertThat(updatedCustomer.getEmail()).isEqualTo(newEmail);
        });
    }

    @Test
    public void addAddress() throws Exception {
        withCustomer(client(), customer -> {
            final String city = "addAddress";
            final Address newAddress = AddressBuilder.of(DE).city(city).build();
            final Predicate<Address> containsNewAddressPredicate = a -> a.getCity().equals(Optional.of(city));
            assertThat(customer.getAddresses().stream()
                    .anyMatch(containsNewAddressPredicate))
                    .overridingErrorMessage("address is not present, yet")
                    .isFalse();
            final Customer updatedCustomer = execute(new CustomerUpdateCommand(customer, AddAddress.of(newAddress)));
            System.out.println(updatedCustomer.getAddresses().stream()
                    .anyMatch(containsNewAddressPredicate));
            assertThat(updatedCustomer.getAddresses().stream()
            .anyMatch(containsNewAddressPredicate)).isTrue();
        });

    }
}