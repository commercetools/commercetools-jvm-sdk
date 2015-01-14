package io.sphere.sdk.customobjects.occexample;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.commands.Command;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.commands.CustomObjectDeleteByContainerAndKeyCommand;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.queries.CustomObjectFetchByKey;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class FlowTest extends IntegrationTest {

    public static final String CONTAINER = CustomerNumberCounter.class.getSimpleName();
    public static final String KEY = "counter";

    @Before
    public void setUp() throws Exception {
        final CustomObjectFetchByKey<JsonNode> fetchByKey = CustomObjectFetchByKey.of(CONTAINER, KEY);
        execute(fetchByKey).ifPresent(o -> execute(CustomObjectDeleteByContainerAndKeyCommand.of(o)));
    }

    @Test
    public void flow() throws Exception {
        setupInitialValue();
        doAnUpdate();
    }

    private void doAnUpdate() {
        final CustomObjectFetchByKey<CustomerNumberCounter> fetch =
                CustomObjectFetchByKey.of(CONTAINER, KEY, CustomerNumberCounter.customObjectTypeReference());

        final CustomObject<CustomerNumberCounter> loadedCustomObject = execute(fetch).get();
        final long newCustomerNumber = loadedCustomObject.getValue().getLastUsedNumber() + 1;
        final CustomerNumberCounter value = new CustomerNumberCounter(newCustomerNumber, "whateverid");
        final long version = loadedCustomObject.getVersion();
        final CustomObjectDraft<CustomerNumberCounter> draft = CustomObjectDraft.ofVersionedUpsert(CONTAINER, KEY, value, version, CustomerNumberCounter.customObjectTypeReference());
        final CustomObjectUpsertCommand<CustomerNumberCounter> updateCommand = CustomObjectUpsertCommand.of(draft);
        final CustomObject<CustomerNumberCounter> updatedCustomObject = execute(updateCommand);
        assertThat(updatedCustomObject.getValue().getLastUsedNumber()).isEqualTo(newCustomerNumber);

        try {
            execute(updateCommand);
            assertThat(true).overridingErrorMessage("optimistic concurrency control").isFalse();
        } catch (final ConcurrentModificationException e) {
            //start again at the top
            assertThat(true).overridingErrorMessage("If other nodes have same version saved, they have to fetch again the object and use the new version number").isTrue();
        }
    }

    private void setupInitialValue() {
        final int lastUsedNumber = 999;
        final CustomerNumberCounter value = new CustomerNumberCounter(lastUsedNumber, "<no name>");
        final int initialVersionNumber = 0;
        final CustomObjectDraft<CustomerNumberCounter> draft = //important: it takes version as parameter
                CustomObjectDraft.ofVersionedUpsert(CONTAINER, KEY, value, initialVersionNumber, CustomerNumberCounter.customObjectTypeReference());

        final Command<CustomObject<CustomerNumberCounter>> initialSettingCommand = CustomObjectUpsertCommand.of(draft);

        final CustomObject<CustomerNumberCounter> initialCounterLoaded = execute(initialSettingCommand);
        assertThat(initialCounterLoaded.getValue().getLastUsedNumber())
                .overridingErrorMessage("We have created the object")
                .isEqualTo(lastUsedNumber);
        assertThat(initialCounterLoaded.getVersion())
                .overridingErrorMessage("the command increments the version number by 1")
                .isEqualTo(initialVersionNumber + 1);

        try {
            execute(initialSettingCommand);
            assertThat(true).overridingErrorMessage("execute the initial command a second time will throw an exception").isFalse();
        } catch (final ConcurrentModificationException e) {
            assertThat(true).overridingErrorMessage("Even in a distributed system the nodes will not override existing values with the initial value.").isTrue();
        }
    }
}
