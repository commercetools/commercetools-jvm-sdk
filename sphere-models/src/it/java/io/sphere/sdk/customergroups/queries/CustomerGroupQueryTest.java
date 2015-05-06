package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;

public class CustomerGroupQueryTest extends IntegrationTest {

    @Test
    public void byName() throws Exception {
        withCustomerGroup(client(), customerGroup -> {
            final PagedQueryResult<CustomerGroup> result = execute(CustomerGroupQuery.of().byName(customerGroup.getName()));
            final Optional<CustomerGroup> found = result.getResults().stream()
                    .filter(cg -> cg.hasSameIdAs(customerGroup))
                    .findFirst();
            assertThat(found).isPresent();
        });
    }
}