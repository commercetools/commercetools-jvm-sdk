package io.sphere.sdk.customers.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.queries.CartByCustomerIdGet;
import io.sphere.sdk.customergroups.queries.CustomerGroupByKeyGet;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerIntegrationTest;
import io.sphere.sdk.customers.CustomerSignInResult;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.newCustomerDraft;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerDeleteCommandIntegrationTest extends CustomerIntegrationTest {
    @Test
    public void execution() throws Exception {
        final CustomerSignInResult result = client().executeBlocking(CustomerCreateCommand.of(newCustomerDraft()));
        final Customer customer = result.getCustomer();
        client().executeBlocking(CustomerDeleteCommand.of(customer));
        final Cart cart = client().executeBlocking(CartByCustomerIdGet.of(customer));
        assertThat(cart).isNull();
    }

    @Test
    public void deleteByKey() throws Exception {
        final String key = randomKey();
        final CustomerSignInResult result = client().executeBlocking(CustomerCreateCommand.of(newCustomerDraft().withKey(key)));
        final Customer customer = result.getCustomer();
        client().executeBlocking(CustomerDeleteCommand.ofKey(key, customer.getVersion()));
        assertThat(client().executeBlocking(CustomerGroupByKeyGet.of(key))).isNull();
    }

    @Test
    public void executionEraseData() throws Exception {
        final CustomerSignInResult result = client().executeBlocking(CustomerCreateCommand.of(newCustomerDraft()));
        final Customer customer = result.getCustomer();
        client().executeBlocking(CustomerDeleteCommand.of(customer,true));
        final Cart cart = client().executeBlocking(CartByCustomerIdGet.of(customer));
        assertThat(cart).isNull();
    }

    @Test
    public void deleteByKeyEraseData() throws Exception {
        final String key = randomKey();
        final CustomerSignInResult result = client().executeBlocking(CustomerCreateCommand.of(newCustomerDraft().withKey(key)));
        final Customer customer = result.getCustomer();
        client().executeBlocking(CustomerDeleteCommand.ofKey(key, customer.getVersion(),true));
        assertThat(client().executeBlocking(CustomerGroupByKeyGet.of(key))).isNull();
    }

}