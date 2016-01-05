package io.sphere.sdk.models;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.queries.CustomerByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomerInGroup;
import static org.assertj.core.api.Assertions.assertThat;

public class ReferenceIntegrationTest extends IntegrationTest {
    @Test
    public void referencesAreNotByDefaultExpanded() {
        withCustomerInGroup(client(), (customer, customerGroup) -> {
            final CustomerByIdGet customerByIdGet = CustomerByIdGet.of(customer);
            final Customer loadedCustomer = client().executeBlocking(customerByIdGet);
            final Reference<CustomerGroup> customerGroupReference = loadedCustomer.getCustomerGroup();
            assertThat(customerGroupReference.getId()).isEqualTo(customerGroup.getId());
            assertThat(customerGroupReference.getObj())
                    .as("reference is not expanded")
                    .isNull();
        });
    }

    @Test
    public void howToExpandReferences() {
        withCustomerInGroup(client(), (customer, customerGroup) -> {
            final CustomerByIdGet customerByIdGet = CustomerByIdGet.of(customer)
                    .withExpansionPaths(m -> m.customerGroup());//the important part
            final Customer loadedCustomer = client().executeBlocking(customerByIdGet);
            final Reference<CustomerGroup> customerGroupReference = loadedCustomer.getCustomerGroup();
            final CustomerGroup loadedCustomerGroup = customerGroupReference.getObj();
            assertThat(loadedCustomerGroup)
                    .as("reference is expanded")
                    .isEqualTo(customerGroup);
        });
    }
}
