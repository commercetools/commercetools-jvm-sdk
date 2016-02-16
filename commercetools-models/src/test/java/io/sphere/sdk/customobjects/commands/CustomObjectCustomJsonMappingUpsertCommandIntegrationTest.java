package io.sphere.sdk.customobjects.commands;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.demo.GsonFoo;
import io.sphere.sdk.customobjects.demo.GsonFooCustomObjectDraft;
import io.sphere.sdk.customobjects.demo.GsonFooCustomObjectUpsertCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectCustomJsonMappingUpsertCommandIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final GsonFoo value = new GsonFoo("bar", 5L);
        final GsonFooCustomObjectDraft draft = new GsonFooCustomObjectDraft("container", "key", value);
        final GsonFooCustomObjectUpsertCommand command = new GsonFooCustomObjectUpsertCommand(draft);
        final CustomObject<GsonFoo> customObject = client().executeBlocking(command);
        final GsonFoo actualValue = customObject.getValue();
        assertThat(actualValue).isEqualTo(value);
    }
}