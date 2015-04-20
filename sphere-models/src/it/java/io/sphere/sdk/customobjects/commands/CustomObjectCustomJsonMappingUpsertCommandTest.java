package io.sphere.sdk.customobjects.commands;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.demo.GsonFoo;
import io.sphere.sdk.customobjects.demo.GsonFooCustomObjectDraft;
import io.sphere.sdk.customobjects.demo.GsonFooCustomObjectUpsertCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CustomObjectCustomJsonMappingUpsertCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final GsonFooCustomObjectDraft draft = new GsonFooCustomObjectDraft("container", "key", new GsonFoo("bar", 5));
        final GsonFooCustomObjectUpsertCommand command = new GsonFooCustomObjectUpsertCommand(draft);
        final CustomObject<GsonFoo> customObject = execute(command);
        assertThat(customObject.getValue().getBaz()).isEqualTo(5);
    }
}