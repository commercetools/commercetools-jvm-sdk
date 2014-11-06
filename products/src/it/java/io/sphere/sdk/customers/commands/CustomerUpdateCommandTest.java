package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerName;
import io.sphere.sdk.customers.commands.updateactions.AddAddress;
import io.sphere.sdk.customers.commands.updateactions.ChangeAddress;
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
import static io.sphere.sdk.test.OptionalAssert.assertThat;
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
            assertThat(updatedCustomer.getAddresses().stream()
            .anyMatch(containsNewAddressPredicate)).isTrue();
        });
    }

    @Test
    public void changeAddress() throws Exception {
        withCustomer(client(), customer -> {
            final Predicate<Address> containsOldAddressPredicate = a -> a.getCity().equals(Optional.of("initialAddress"));
            final Predicate<Address> containsNewAddressPredicate = a -> a.getCity().equals(Optional.of("newAddress"));

            final Address address = AddressBuilder.of(DE).city("initialAddress").build();
            final Customer customerWithAddress = execute(new CustomerUpdateCommand(customer, AddAddress.of(address)));

            assertThat(customerWithAddress.getAddresses()).hasSize(1);
            assertThat(customerWithAddress.getAddresses().stream()
                    .anyMatch(containsOldAddressPredicate))
                    .overridingErrorMessage("customer is initialized with an address")
                    .isTrue();
            assertThat(customerWithAddress.getAddresses().stream()
                    .anyMatch(containsNewAddressPredicate))
                    .overridingErrorMessage("customer has not yet the new address")
                    .isFalse();

            final Address oldAddress = customerWithAddress.getAddresses().stream()
                    .filter(containsOldAddressPredicate).findFirst().get();

            assertThat(oldAddress.getId())
                    .overridingErrorMessage("only fetched address contains an id")
                    .isPresent();

            final Address newAddress = AddressBuilder.of(DE).city("newAddress").build();
            final ChangeAddress updateAction = ChangeAddress.ofOldAddressToNewAddress(oldAddress, newAddress);
            final Customer customerWithReplacedAddress = execute(new CustomerUpdateCommand(customerWithAddress, updateAction));

            assertThat(customerWithReplacedAddress.getAddresses()).hasSize(1);
            assertThat(customerWithReplacedAddress.getAddresses().stream()
                    .anyMatch(containsOldAddressPredicate))
                    .overridingErrorMessage("old address is absent")
                    .isFalse();
            assertThat(customerWithReplacedAddress.getAddresses().stream()
                    .anyMatch(containsNewAddressPredicate))
                    .overridingErrorMessage("new address is present")
                    .isTrue();
        });
    }
}