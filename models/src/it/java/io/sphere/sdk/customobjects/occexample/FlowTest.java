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
import org.fest.assertions.Assertions;
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
    }

    private void setupInitialValue() {
        final int lastUsedNumber = 999;
        final CustomerNumberCounter value = new CustomerNumberCounter(lastUsedNumber, "<no name>");
        final int initialVersionNumber = 0;
        final CustomObjectDraft<CustomerNumberCounter> draft = //important: it takes version as parameter
                CustomObjectDraft.of(CONTAINER, KEY, value, initialVersionNumber, CustomerNumberCounter.typeReference());

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
            assertThat(true).overridingErrorMessage("Even in a distributed system the nodes will not override the values.").isTrue();
        }
    }
}
