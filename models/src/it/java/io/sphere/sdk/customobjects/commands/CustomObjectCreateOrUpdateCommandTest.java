package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;


import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

public class CustomObjectCreateOrUpdateCommandTest extends IntegrationTest {
    @Test
    public void createSimpleNew() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), co -> {

        });
    }

    @Test
    public void updateWithoutVersion() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), co -> {
            final TypeReference<CustomObject<Foo>> typeReference = Foo.customObjectTypeReference();
            final Foo newValue = new Foo("new value", 72);
            final CustomObjectDraft<Foo> draft = CustomObjectDraft.ofUnversionedDraft(co, newValue, typeReference);
            final CustomObjectCreateOrUpdateCommand<Foo> createCommand = CustomObjectCreateOrUpdateCommand.of(draft);
            final CustomObject<Foo> customObject = execute(createCommand);
            assertThat(customObject.getValue()).isEqualTo(newValue);
        });
    }

    @Test
    public void updateWithVersion() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), co -> {
            final TypeReference<CustomObject<Foo>> typeReference = Foo.customObjectTypeReference();
            final Foo newValue = new Foo("new value", 72);
            final CustomObjectDraft<Foo> draft = CustomObjectDraft.ofVersionedDraft(co, newValue, typeReference);
            final CustomObjectCreateOrUpdateCommand<Foo> command = CustomObjectCreateOrUpdateCommand.of(draft);
            final CustomObject<Foo> customObject = execute(command);
            assertThat(customObject.getValue()).isEqualTo(newValue);
            try {
                execute(command);
                fail("exception should be thrown");
            } catch (final Exception e) {
                assertThat(e.getCause().getCause()).isInstanceOf(ConcurrentModificationException.class);
            }
        });
    }
}