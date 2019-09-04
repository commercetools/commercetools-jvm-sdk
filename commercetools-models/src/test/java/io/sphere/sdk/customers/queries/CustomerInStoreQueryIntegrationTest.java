package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerInStoreQueryIntegrationTest extends IntegrationTest {

    @Test
    public void byKey() throws Exception {
        CustomerFixtures.withCustomerInStore(client(), customer -> {
            final PagedQueryResult<Customer> result = client().executeBlocking(CustomerInStoreQuery.of(customer.getStores().get(0).getKey())
                    .withPredicates(m -> m.key().is(customer.getKey())));
            assertThat(result.getResults().get(0).getId()).isEqualTo(customer.getId());
        });
    }
}