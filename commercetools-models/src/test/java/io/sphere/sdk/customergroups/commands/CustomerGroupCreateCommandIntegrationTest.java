package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.CustomerGroupDraft;
import io.sphere.sdk.customergroups.CustomerGroupFixtures;
import io.sphere.sdk.customergroups.queries.CustomerGroupQuery;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class CustomerGroupCreateCommandIntegrationTest extends IntegrationTest {
    @BeforeClass
    public static void clean() {
        client().executeBlocking(CustomerGroupQuery.of().byName("demo-customer-group"))
                .getResults()
                .forEach(group -> client().executeBlocking(CustomerGroupDeleteCommand.of(group)));
    }

    @Test
    public void execution() {
        final String name = "creation demo customer group name";

        final CustomerGroup customerGroup = client().executeBlocking(CustomerGroupCreateCommand.of(name));

        assertThat(customerGroup.getName()).isEqualTo(name);
    }

    @Test
    public void createByJson() {
        final CustomerGroupDraft draft = SphereJsonUtils.readObjectFromResource("drafts-tests/customerGroup.json", CustomerGroupDraft.class);
        withCustomerGroup(client(), draft, customerGroup -> {
            assertThat(customerGroup.getName()).isEqualTo("demo-customer-group");
        });
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