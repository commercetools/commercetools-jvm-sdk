package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.*;
import io.sphere.sdk.customers.commands.CustomerCreateCommand;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.SortDirection;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.*;

import java.util.List;
import java.util.function.Function;

import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.stream.Collectors.toList;

public class CustomerQueryTest extends IntegrationTest {
    private static Customer customer;
    private static Customer distraction;

    @BeforeClass
    public static void setUpCustomer() throws Exception {
        customer = createCustomer("John", "Smith", "shippingId1");
        distraction = createCustomer("Missy", "Jones", "shippingId2");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        customer = null;
        distraction = null;
    }

    @Test
    public void email() throws Exception {
        check((model) -> model.email().is(customer.getEmail()));
    }

    @Test
    public void firstName() throws Exception {
        check((model) -> model.firstName().is(customer.getFirstName()));
    }

    @Test
    public void lastName() throws Exception {
        check((model) -> model.lastName().is(customer.getLastName()));
    }

    private void check(final Function<CustomerQueryModel, Predicate<Customer>> f) {
        final CustomerQueryModel model = CustomerQuery.model();
        final Predicate<Customer> predicate = f.apply(model);
        final Query<Customer> query = new CustomerQuery().withPredicate(predicate).withSort(model.createdAt().sort(SortDirection.DESC));
        final List<Customer> results = execute(query).getResults();
        final List<String> ids = results.stream().map(x -> x.getId()).collect(toList());
        assertThat(ids).contains(customer.getId());
        assertThat(ids.contains(distraction.getId())).isFalse();
    }

    private static Customer createCustomer(final String firstName, final String lastName, final String defaultShippingAddressId) {
        final CustomerName customerName = CustomerName.ofFirstAndLastName(firstName, lastName);
        final CustomerDraft draft = CustomerDraft.of(customerName, randomEmail(CustomerQueryTest.class), "secret");
        final CustomerSignInResult signInResult = execute(new CustomerCreateCommand(draft));
        final Customer initialCustomer = signInResult.getCustomer();
        return initialCustomer;
    }
}