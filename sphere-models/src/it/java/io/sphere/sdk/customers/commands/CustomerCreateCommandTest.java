package io.sphere.sdk.customers.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.CustomerGroupFixtures;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerName;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.customers.CustomerFixtures.*;

public class CustomerCreateCommandTest extends IntegrationTest {

    @Test
    public void createCustomer() throws Exception {
        final CustomerGroup customerGroup = CustomerGroupFixtures.b2cCustomerGroup(client());
        final CustomerName name = CustomerName.ofFirstAndLastName("John", "Smith");
        final String email = randomEmail(CustomerCreateCommandTest.class);
        final String externalId = randomString();
        final String password = "secret";
        final LocalDate dateOfBirth = LocalDate.of(1985, 5, 7);
        final String companyName = "ct";
        final String vatId = "123456";
        final boolean emailVerified = true;
        final List<Address> addresses = asList(Address.of(DE), Address.of(GB));
        final CustomerDraft draft = CustomerDraft.of(name, email, password)
                .withExternalId(externalId)
                .withDateOfBirth(dateOfBirth)
                .withCompanyName(companyName)
                .withVatId(vatId)
                .withEmailVerified(emailVerified)
                .withCustomerGroup(customerGroup)
                .withAddresses(addresses)
                .withDefaultBillingAddress(0)
                .withDefaultShippingAddress(1);
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
        assertThat(customer.getDateOfBirth()).contains(dateOfBirth);
        assertThat(customer.getCompanyName()).contains(companyName);
        assertThat(customer.getVatId()).contains(vatId);
        assertThat(customer.isEmailVerified()).isEqualTo(emailVerified);
        assertThat(customer.getCustomerGroup()).contains(customerGroup.toReference());
        assertThat(customer.getAddresses().stream().map(a -> a.withId(Optional.empty())).collect(toList())).isEqualTo(addresses);
        assertThat(customer.getDefaultBillingAddress().get().withId(Optional.empty())).isEqualTo(addresses.get(0));
        assertThat(customer.getDefaultShippingAddress().get().withId(Optional.empty())).isEqualTo(addresses.get(1));
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