package io.sphere.sdk.customers.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customers.*;
import io.sphere.sdk.customers.commands.CustomerCreateCommand;
import io.sphere.sdk.customers.commands.CustomerUpdateCommand;
import io.sphere.sdk.customers.commands.updateactions.AddAddress;
import io.sphere.sdk.customers.commands.updateactions.SetCustomerGroup;
import io.sphere.sdk.customers.commands.updateactions.SetDefaultBillingAddress;
import io.sphere.sdk.customers.commands.updateactions.SetDefaultShippingAddress;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.b2cCustomerGroup;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomerInGroup;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerQueryIntegrationTest extends IntegrationTest {
    public static final String FIRST_NAME = "John";
    private static Customer customer;
    private static Customer distraction;

    @BeforeClass
    public static void setUpCustomer() throws Exception {
        customer = createCustomer(FIRST_NAME, "Smith","von","Mr.");
        distraction = createCustomer("Missy", "Jones","Bin","Mrs.");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        customer = null;
        distraction = null;
    }

    @Test
    public void customerGroupReferenceExpansion() throws Exception {
        withCustomerInGroup(client(), (customer, customerGroup) -> {
            final Query<Customer> query = CustomerQuery.of()
                    .byEmail(customer.getEmail())
                    .withExpansionPaths(m -> m.customerGroup());
            final String actualCustomerGroupId = client().executeBlocking(query).head().get().getCustomerGroup().getObj().getId();
            assertThat(actualCustomerGroupId).isEqualTo(customerGroup.getId());
        });

    }

    @Test
    public void byEmailMethod() throws Exception {
        final PagedQueryResult<Customer> result = client().executeBlocking(CustomerQuery.of().byEmail(customer.getEmail()));
        assertThat(result.getResults().get(0).getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    public void lowercaseEmail() throws Exception {
        final PagedQueryResult<Customer> result = client().executeBlocking(CustomerQuery.of()
                .withPredicates(m -> m.lowercaseEmail().is(customer.getLowercaseEmail())));
        assertThat(result.getResults().get(0).getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    public void email() throws Exception {
        final PagedQueryResult<Customer> result = client().executeBlocking(CustomerQuery.of()
                .withPredicates(m -> m.email().is(customer.getEmail())));
        assertThat(result.getResults().get(0).getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    public void emailHelper() throws Exception {
        check((model) -> model.email().is(customer.getEmail()));
    }
    
    @Test
    public void key() throws Exception {
        check((model) -> model.key().is(customer.getKey()));
    }

    @Test
    public void middleName() throws Exception {
        check((model) -> model.middleName().is(customer.getMiddleName()));
    }

    @Test
    public void title() throws Exception {
        check((model) -> model.title().is(customer.getTitle()));
    }

    @Test
    public void firstName() throws Exception {
        check((model) -> model.firstName().is(customer.getFirstName()));
    }

    @Test
    public void lastName() throws Exception {
        check((model) -> model.lastName().is(customer.getLastName()));
    }

    @Test
    public void defaultShippingAddressId() throws Exception {
        check((model) -> model.defaultShippingAddressId().is(customer.getDefaultShippingAddressId()));
    }

    @Test
    public void defaultBillingAddressId() throws Exception {
        check((model) -> model.defaultBillingAddressId().is(customer.getDefaultBillingAddressId()));
    }

    @Test
    public void isEmailVerified() throws Exception {
        check((model) -> model.isEmailVerified().is(false), false);
    }

    @Test
    public void externalId() throws Exception {
        check((model) -> model.externalId().is(customer.getExternalId()));
    }

    @Test
    public void customerNumber() throws Exception {
        check((model) -> model.customerNumber().is(customer.getCustomerNumber()));
    }

    @Test
    public void customerGroup() throws Exception {
        check((model) -> model.customerGroup().is(customer.getCustomerGroup()), false);
    }

    @Test
    public void locale() {
        check((model) -> model.locale().is(Locale.GERMAN));
    }

    private void check(final Function<CustomerQueryModel, QueryPredicate<Customer>> f) {
        check(f, false);
    }

    private void check(final Function<CustomerQueryModel, QueryPredicate<Customer>> f, final boolean checkDistraction) {
        final CustomerQueryModel model = CustomerQueryModel.of();
        final QueryPredicate<Customer> predicate = f.apply(model);
        final Query<Customer> query = CustomerQuery.of().withPredicates(predicate).withSort(model.createdAt().sort().desc());
        final List<Customer> results = client().executeBlocking(query).getResults();
        final List<String> ids = results.stream().map(x -> x.getId()).collect(toList());
        assertThat(ids).contains(customer.getId());
        if (checkDistraction) {
            assertThat(ids.contains(distraction.getId())).isFalse();
        }
    }

    private static Customer createCustomer(final String firstName, final String lastName,final String middleName, final String title) {
        final CustomerName customerName = CustomerName.ofFirstAndLastName(firstName, lastName);
        final CustomerDraft draft = CustomerDraftDsl.of(customerName, randomEmail(CustomerQueryIntegrationTest.class), "secret")
                .withLocale(Locale.GERMAN)
                .withMiddleName(middleName)
                .withTitle(title)
                .withExternalId(randomString()+firstName)
                .withCustomerNumber(randomKey())
                .withKey(randomKey());
        final CustomerSignInResult signInResult = client().executeBlocking(CustomerCreateCommand.of(draft));
        final Customer initialCustomer = signInResult.getCustomer();

        final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(initialCustomer, asList(AddAddress.of(AddressBuilder.of(CountryCode.DE).city(randomString()).build()), SetCustomerGroup.of(b2cCustomerGroup(client())))));

        final SetDefaultShippingAddress shippingAddressAction = SetDefaultShippingAddress.ofAddress(updatedCustomer.getAddresses().get(0));
        final SetDefaultBillingAddress billingAddressAction = SetDefaultBillingAddress.ofAddress(updatedCustomer.getAddresses().get(0));
        return client().executeBlocking(CustomerUpdateCommand.of(updatedCustomer, asList(shippingAddressAction, billingAddressAction)));
    }
}