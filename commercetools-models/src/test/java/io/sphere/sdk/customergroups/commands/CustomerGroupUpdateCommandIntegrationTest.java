package io.sphere.sdk.customergroups.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.commands.updateactions.ChangeName;
import io.sphere.sdk.customergroups.commands.updateactions.SetCustomType;
import io.sphere.sdk.customergroups.commands.updateactions.SetKey;
import io.sphere.sdk.customergroups.queries.CustomerGroupQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.sphere.sdk.test.SphereTestUtils.*;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static io.sphere.sdk.types.TypeFixtures.LOC_STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerGroupUpdateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void changeName() throws Exception {
        withCustomerGroup(client(), customerGroup -> {
            final String newName = randomString();
            assertThat(customerGroup.getName()).isNotEqualTo(newName);
            final CustomerGroup updatedCustomerGroup = client().executeBlocking(CustomerGroupUpdateCommand.of(customerGroup, ChangeName.of(newName)));
            assertThat(updatedCustomerGroup.getName()).isEqualTo(newName);
            return updatedCustomerGroup;
        });
    }

    @Test
    public void setKey() throws Exception {
        withCustomerGroup(client(), customerGroup -> {
            final String newKey = randomKey();
            assertThat(customerGroup.getKey()).isNotEqualTo(newKey);
            final CustomerGroup updatedCustomerGroup = client().executeBlocking(CustomerGroupUpdateCommand.of(customerGroup, SetKey.of(newKey)));
            assertThat(updatedCustomerGroup.getKey()).isEqualTo(newKey);
            return updatedCustomerGroup;
        });
    }

    @Test
    public void setCustomField() throws Exception {

        withUpdateableType(client(), type -> {
            withCustomerGroup(client(), customerGroup -> {
                final Map<String, Object> fields = new HashMap<>();
                fields.put(STRING_FIELD_NAME, "foo");
                fields.put(LOC_STRING_FIELD_NAME, LocalizedString.of(ENGLISH, "bar"));
                final CustomerGroupUpdateCommand customerGroupUpdateCommand = CustomerGroupUpdateCommand.of(customerGroup, SetCustomType.ofTypeIdAndObjects(type.getId(), fields));
                final CustomerGroup updatedCustomerGroup = client().executeBlocking(customerGroupUpdateCommand);
                final CustomerGroupQuery categoryQuery = CustomerGroupQuery.of()
                        .plusPredicates(m -> m.is(customerGroup))
                        .plusPredicates(m -> m.custom().fields().ofString(STRING_FIELD_NAME).is("foo"))
                        .plusPredicates(m -> m.custom().fields().ofString("njetpresent").isNotPresent())
                        .plusPredicates(m -> m.custom().fields().ofLocalizedString(LOC_STRING_FIELD_NAME).locale(ENGLISH).is("bar"));

                assertThat(client().executeBlocking(categoryQuery).head()).contains(updatedCustomerGroup);
                return updatedCustomerGroup;
            });
            return type;
        });

    }

}