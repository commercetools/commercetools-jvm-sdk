package io.sphere.sdk.customobjects.occexample;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.Command;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.commands.CustomObjectDeleteCommand;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyGet;
import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FlowIntegrationTest extends IntegrationTest {

    public static final String CONTAINER = CustomerNumberCounter.class.getSimpleName();
    public static final String KEY = "counter";

    @Before
    public void setUp() throws Exception {
        final CustomObjectByKeyGet<JsonNode> fetchByKey = CustomObjectByKeyGet.ofJsonNode(CONTAINER, KEY);
        Optional.ofNullable(client().executeBlocking(fetchByKey)).ifPresent(o -> client().executeBlocking(CustomObjectDeleteCommand.ofJsonNode(o)));
    }

    @Test
    public void flow() throws Exception {
        setupInitialValue();
        doAnUpdate();
    }

    private void doAnUpdate() {
        final CustomObjectByKeyGet<CustomerNumberCounter> fetch =
                CustomObjectByKeyGet.of(CONTAINER, KEY, CustomerNumberCounter.class);

        final CustomObject<CustomerNumberCounter> loadedCustomObject = client().executeBlocking(fetch);
        final long newCustomerNumber = loadedCustomObject.getValue().getLastUsedNumber() + 1;
        final CustomerNumberCounter value = new CustomerNumberCounter(newCustomerNumber, "whateverid");
        final long version = loadedCustomObject.getVersion();
        final CustomObjectDraft<CustomerNumberCounter> draft = CustomObjectDraft.ofVersionedUpsert(CONTAINER, KEY, value, version, CustomerNumberCounter.class);
        final CustomObjectUpsertCommand<CustomerNumberCounter> updateCommand = CustomObjectUpsertCommand.of(draft);
        final CustomObject<CustomerNumberCounter> updatedCustomObject = client().executeBlocking(updateCommand);
        assertThat(updatedCustomObject.getValue().getLastUsedNumber()).isEqualTo(newCustomerNumber);

        try {
            client().executeBlocking(updateCommand);
            assertThat(true).overridingErrorMessage("optimistic concurrency control").isFalse();
        } catch (final ConcurrentModificationException e) {
            //start again at the top
            assertThat(true).overridingErrorMessage("If other nodes have same version saved, they have to fetch again the object and use the new version number").isTrue();
        }
    }

    private void setupInitialValue() {
        final long lastUsedNumber = 999;
        final CustomerNumberCounter value = new CustomerNumberCounter(lastUsedNumber, "<no name>");
        final long initialVersionNumber = 0;
        final CustomObjectDraft<CustomerNumberCounter> draft = //important: it takes version as parameter
                CustomObjectDraft.ofVersionedUpsert(CONTAINER, KEY, value, initialVersionNumber, CustomerNumberCounter.class);

        final Command<CustomObject<CustomerNumberCounter>> initialSettingCommand = CustomObjectUpsertCommand.of(draft);

        final CustomObject<CustomerNumberCounter> initialCounterLoaded = client().executeBlocking(initialSettingCommand);
        assertThat(initialCounterLoaded.getValue().getLastUsedNumber())
                .overridingErrorMessage("We have created the object")
                .isEqualTo(lastUsedNumber);
        assertThat(initialCounterLoaded.getVersion())
                .overridingErrorMessage("the command increments the version number by 1")
                .isEqualTo(initialVersionNumber + 1);

        try {
            client().executeBlocking(initialSettingCommand);
            assertThat(true).overridingErrorMessage("execute the initial command a second time will throw an exception").isFalse();
        } catch (final ConcurrentModificationException e) {
            assertThat(true).overridingErrorMessage("Even in a distributed system the nodes will not override existing values with the initial value.").isTrue();
        }
    }
}
