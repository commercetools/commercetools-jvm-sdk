package io.sphere.sdk.customers.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.CustomerGroupFixtures;
import io.sphere.sdk.customers.*;
import io.sphere.sdk.customers.queries.CustomerQuery;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.test.JsonNodeReferenceResolver;
import net.jcip.annotations.NotThreadSafe;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static com.neovisionaries.i18n.CountryCode.FR;
import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withB2cCustomerGroup;
import static io.sphere.sdk.customers.CustomerFixtures.CUSTOMER_NAME;
import static io.sphere.sdk.customers.CustomerFixtures.PASSWORD;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class CustomerCreateCommandIntegrationTest extends CustomerIntegrationTest {
    @BeforeClass
    public static void clean() {
        client().executeBlocking(CustomerQuery.of().byEmail("osgood@unit.tld"))
                .getResults()
                .forEach(customer -> client().executeBlocking(CustomerDeleteCommand.of(customer)));
    }

    @Test
    public void createCustomer() throws Exception {
        final CustomerGroup customerGroup = CustomerGroupFixtures.b2cCustomerGroup(client());
        final CustomerName name = CustomerName.ofFirstAndLastName("John", "Smith");
        final String email = randomEmail(CustomerCreateCommandIntegrationTest.class);
        final String externalId = randomString();
        final String password = "secret";
        final LocalDate dateOfBirth = LocalDate.of(1985, 5, 7);
        final String companyName = "ct";
        final String vatId = "123456";
        final boolean emailVerified = true;
        final List<Address> addresses = asList(Address.of(DE), Address.of(GB), Address.of(US), Address.of(FR));
        final CustomerDraft draft = CustomerDraftDsl.of(name, email, password)
                .withLocale(GERMAN)
                .withExternalId(externalId)
                .withDateOfBirth(dateOfBirth)
                .withCompanyName(companyName)
                .withVatId(vatId)
                .withEmailVerified(emailVerified)
                .withCustomerGroup(customerGroup)
                .withAddresses(addresses)
                .withDefaultBillingAddress(0)
                .withDefaultShippingAddress(1)
                .withShippingAddresses(singletonList(2))
                .withBillingAddresses(singletonList(3));
        final CustomerCreateCommand sphereRequest = CustomerCreateCommand.of(draft)
                .withExpansionPaths(m -> m.customer().customerGroup());
        final CustomerSignInResult result = client().executeBlocking(sphereRequest);
        assertThat(result.getCart())
                .as("no cart id given in creation, so this field is empty")
                .isNull();
        final Customer customer = result.getCustomer();
        final Cart cart = result.getCart();
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getPassword())
                .as("password is not stored in clear text")
                .isNotEqualTo(password);
        assertThat(customer.getExternalId()).contains(externalId);
        assertThat(customer.getLocale()).isEqualTo(GERMAN);
        assertThat(cart).isNull();
        assertThat(customer.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(customer.getCompanyName()).contains(companyName);
        assertThat(customer.getVatId()).contains(vatId);
        assertThat(customer.isEmailVerified()).isEqualTo(emailVerified);
        assertThat(customer.getCustomerGroup()).isEqualTo(customerGroup.toReference());
        final List<Address> loadedAddresses = customer.getAddresses();
        assertThat(loadedAddresses.stream().map(a -> a.withId(null)).collect(toList())).isEqualTo(addresses);
        assertThat(customer.getDefaultBillingAddress().withId(null)).isEqualTo(addresses.get(0));
        assertThat(customer.findDefaultShippingAddress().get().withId(null)).isEqualTo(addresses.get(1));
        assertThat(customer.getBillingAddressIds())
                .containsExactly(loadedAddresses.get(0).getId(), loadedAddresses.get(3).getId());
        assertThat(customer.getBillingAddresses()).extracting(Address::getCountry).containsExactly(DE, FR);
        assertThat(customer.getShippingAddressIds())
                .containsExactly(loadedAddresses.get(1).getId(), loadedAddresses.get(2).getId());
        assertThat(customer.getShippingAddresses()).extracting(Address::getCountry).containsExactly(GB, US);
        assertThat(customer.getCustomerGroup().getObj())
                .as("customer group can be expanded")
                .isNotNull();
        String addressId = loadedAddresses.get(0).getId();
        final Address addressById = customer.findAddressById(addressId).get();
        assertThat(addressById.equals(loadedAddresses.get(0)));
    }

    @Test
    public void createCustomerWithCart() throws Exception {
        final Cart cart = client().executeBlocking(CartCreateCommand.of(CartDraft.of(EUR)));//could of course be filled with products
        final String email = randomEmail(CustomerCreateCommandIntegrationTest.class);
        final CustomerDraft draft = CustomerDraftDsl.of(CUSTOMER_NAME, email, PASSWORD).withCart(cart);
        final CustomerSignInResult result = client().executeBlocking(CustomerCreateCommand.of(draft));
        assertThat(result.getCart()).isNotNull();
        assertThat(result.getCart().getId()).isEqualTo(cart.getId());
    }

    @Test
    public void createCustomerWithCartWithCartExpansion() {
        final CustomerGroup customerGroup = CustomerGroupFixtures.b2cCustomerGroup(client());
        final Cart cart = client().executeBlocking(CartCreateCommand.of(CartDraft.of(EUR)));
        final String email = randomEmail(CustomerCreateCommandIntegrationTest.class);
        final CustomerDraft draft = CustomerDraftDsl.of(CUSTOMER_NAME, email, PASSWORD).withCart(cart).withCustomerGroup(customerGroup);
        final CustomerCreateCommand customerCreateCommand = CustomerCreateCommand.of(draft)
                .withExpansionPaths(m -> m.cart().customerGroup());
        final CustomerSignInResult result = client().executeBlocking(customerCreateCommand);
        assertThat(result.getCart()).isNotNull();
        assertThat(result.getCart().getId()).isEqualTo(cart.getId());
        assertThat(result.getCart().getCustomerGroup().getObj()).isEqualTo(customerGroup);
    }

    @Test
    public void createByJson() {
        final JsonNodeReferenceResolver referenceResolver = new JsonNodeReferenceResolver();
        withB2cCustomerGroup(client(), customerGroup -> {
            referenceResolver.addResourceByKey("b2c", customerGroup);
            final CustomerDraft customerDraft = draftFromJsonResource("drafts-tests/customer.json", CustomerDraftDsl.class, referenceResolver);
            CustomerFixtures.withCustomer(client(), customerDraft, customer -> {
                assertThat(customer.getLastName()).isEqualTo("Osgood");
                assertThat(customer.getCustomerGroup()).isEqualTo(customerGroup.toReference());
                assertThat(customer.getAddresses()).hasSize(2);
                assertThat(customer.getDefaultShippingAddress().withId(null))
                        .isEqualTo(Address.of(DE).withLastName("Osgood").withExternalId("external-ID-DE"));
            });
        });

    }

    @Test
    public void anonymousId() {
        final String anonymousId = randomKey();
        final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE).withAnonymousId(anonymousId);
        final Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));
        final String email = randomEmail(CustomerCreateCommandIntegrationTest.class);
        final CustomerDraft customerDraft = CustomerDraftBuilder
                .of(email, "secret")
                .anonymousId(anonymousId)
                .build();

        final CustomerSignInResult customerSignInResult = client().executeBlocking(CustomerCreateCommand.of(customerDraft));

        assertThat(customerSignInResult.getCustomer().getEmail()).isEqualTo(email);
        assertThat(customerSignInResult.getCart().getId())
                .as("the customer gets the cart from the anonymous session assigned on sign-up")
                .isEqualTo(cart.getId());
    }

    @Test
    public void getShippingAddressIdsInEmptyCase() {
        final CustomerDraft draft = CustomerDraftDsl.of(randomEmail(getClass()), "secret");
        final CustomerSignInResult customerSignInResult =
                client().executeBlocking(CustomerCreateCommand.of(draft));
        final Customer customer = customerSignInResult.getCustomer();
        assertThat(customer.getBillingAddressIds()).isNotNull().isEmpty();
        assertThat(customer.getShippingAddressIds()).isNotNull().isEmpty();
        assertThat(customer.getAddresses()).isNotNull().isEmpty();
        assertThat(customer.getShippingAddresses()).isNotNull().isEmpty();
        assertThat(customer.getBillingAddresses()).isNotNull().isEmpty();
    }
}