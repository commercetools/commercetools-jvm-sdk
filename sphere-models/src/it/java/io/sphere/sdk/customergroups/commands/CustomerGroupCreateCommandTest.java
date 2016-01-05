package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.queries.CustomerGroupQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerGroupCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() {
        final String name = "creation demo customer group name";

        final CustomerGroup customerGroup = client().executeBlocking(CustomerGroupCreateCommand.of(name));

        assertThat(customerGroup.getName()).isEqualTo(name);
    }

    @Before
    public void setUp() throws Exception {
        deleteCustomerGroup();

    }

    @After
    public void tearDown() throws Exception {
        deleteCustomerGroup();
    }

    private void deleteCustomerGroup() {
        final PagedQueryResult<CustomerGroup> result = client().executeBlocking(CustomerGroupQuery.of()
                .byName("creation demo customer group name"));
        result.getResults().forEach(customerGroup -> client().executeBlocking(CustomerGroupDeleteCommand.of(customerGroup)));
    }
}