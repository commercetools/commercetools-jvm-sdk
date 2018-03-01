package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.CustomerGroupDraft;
import io.sphere.sdk.customergroups.queries.CustomerGroupQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static org.assertj.core.api.Assertions.*;

public class CustomerGroupDeleteCommandByKeyIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        final String name = randomString();
        final String key = randomKey();
        final CustomerGroupDraft customerGroupDraft = CustomerGroupDraft.of(name,key);
        final CustomerGroup customerGroup = client().executeBlocking(CustomerGroupCreateCommand.of(customerGroupDraft));

        client().executeBlocking(CustomerGroupDeleteCommand.ofKey(customerGroup.getKey(),customerGroup.getVersion()));

        final PagedQueryResult<CustomerGroup> queryResult = client().executeBlocking(CustomerGroupQuery.of().byName(name));
        assertThat(queryResult.getResults()).isEmpty();
    }


}