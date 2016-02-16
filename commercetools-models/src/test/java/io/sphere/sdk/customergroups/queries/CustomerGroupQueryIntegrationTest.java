package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerGroupQueryIntegrationTest extends IntegrationTest {

    @Test
    public void byName() throws Exception {
        withCustomerGroup(client(), customerGroup -> {
            final PagedQueryResult<CustomerGroup> result = client().executeBlocking(CustomerGroupQuery.of().byName(customerGroup.getName()));
            final Optional<CustomerGroup> found = result.getResults().stream()
                    .filter(cg -> cg.hasSameIdAs(customerGroup))
                    .findFirst();
            assertThat(found).isPresent();
        });
    }

    @Test
    public void byNames() throws Exception {
        withCustomerGroup(client(), customerGroupA -> {
            withCustomerGroup(client(), customerGroupB -> {
                final List<String> names = asList(customerGroupA.getName(), customerGroupB.getName());
                final CustomerGroupQuery query = CustomerGroupQuery.of()
                        .withPredicates(group -> group.name().isIn(names));

                final List<CustomerGroup> results = client().executeBlocking(query).getResults();
                assertThat(results).containsOnly(customerGroupA, customerGroupB);
            });
        });
    }
}