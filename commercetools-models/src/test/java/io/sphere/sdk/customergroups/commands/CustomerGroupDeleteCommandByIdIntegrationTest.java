package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.queries.CustomerGroupQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.*;

public class CustomerGroupDeleteCommandByIdIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        final String name = randomKey();
        final CustomerGroup customerGroup = client().executeBlocking(CustomerGroupCreateCommand.of(name));

        client().executeBlocking(CustomerGroupDeleteCommand.of(customerGroup));

        final PagedQueryResult<CustomerGroup> queryResult = client().executeBlocking(CustomerGroupQuery.of().byName(name));
        assertThat(queryResult.getResults()).isEmpty();
    }


}