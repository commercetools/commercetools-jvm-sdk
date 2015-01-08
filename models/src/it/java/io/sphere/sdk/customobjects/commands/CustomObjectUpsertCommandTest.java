package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.customobjects.demo.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;


import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

public class CustomObjectUpsertCommandTest extends IntegrationTest {

    public static final String CONTAINER = CustomObjectUpsertCommandTest.class.getSimpleName();

    @Test
    public void createSimpleNew() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), co -> {

        });
    }

    @Test
    public void pureJson() throws Exception {
        final ObjectMapper objectMapper = JsonUtils.newObjectMapper();//you should cache this one
        final ObjectNode objectNode = objectMapper.createObjectNode()
                .put("bar", " a string")
                .put("baz", 5L);
        final String key = "pure-json";
        final CustomObjectUpsertCommand<JsonNode> command =
                CustomObjectUpsertCommand.of(CustomObjectDraft.of(CONTAINER, key, objectNode));
        final CustomObject<JsonNode> customObject = execute(command);
        assertThat(customObject.getValue().get("bar").asText("default value")).isEqualTo(" a string");
        assertThat(customObject.getValue().get("baz").asLong(0)).isEqualTo(5);
    }

    @Test
    public void storyBinaryData() throws Exception {
        final String name = "hello.pdf";
        final File file = new File(".", "models/src/it/resources/" + name);
        final byte[] bytes = FileUtils.readFileToByteArray(file);
        final BinaryData value = new BinaryData(name, bytes);
        final String key = "storyBinaryData";
        final TypeReference<CustomObject<BinaryData>> typeReference = new TypeReference<CustomObject<BinaryData>>() {
        };
        final CustomObjectUpsertCommand<BinaryData> command = CustomObjectUpsertCommand.of(CustomObjectDraft.of(CONTAINER, key, value, typeReference));
        final BinaryData loadedValue = execute(command).getValue();
        assertThat(loadedValue).isEqualTo(value);
    }

    @Test
    public void storeFlatString() throws Exception {
        final String value = "hello";
        final String key = "storeFlatString";
        final CustomObjectUpsertCommand<String> command = CustomObjectUpsertCommand.of(CustomObjectDraft.of(CONTAINER, key, value, new TypeReference<CustomObject<String>>() {
        }));
        final String loadedValue = execute(command).getValue();
        assertThat(loadedValue).isEqualTo(value);
    }

    @Test
    public void storeLong() throws Exception {
        final long value = 23;
        final String key = "storeLong";
        final CustomObjectUpsertCommand<Long> command = CustomObjectUpsertCommand.of(CustomObjectDraft.of(CONTAINER, key, value, new TypeReference<CustomObject<Long>>() {
        }));
        final long loadedValue = execute(command).getValue();
        assertThat(loadedValue).isEqualTo(value);
    }

    @Test
    public void updateWithoutVersion() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), customObject -> {
            final TypeReference<CustomObject<Foo>> typeReference = Foo.customObjectTypeReference();
            final Foo newValue = new Foo("new value", 72);
            final CustomObjectDraft<Foo> draft = CustomObjectDraft.ofUnversionedDraft(customObject, newValue, typeReference);
            final CustomObjectUpsertCommand<Foo> createCommand = CustomObjectUpsertCommand.of(draft);
            final CustomObject<Foo> updatedCustomObject = execute(createCommand);
            final Foo loadedValue = updatedCustomObject.getValue();
            assertThat(loadedValue).isEqualTo(newValue);
        });
    }

    @Test
    public void updateWithVersion() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), customObject -> {
            final TypeReference<CustomObject<Foo>> typeReference = Foo.customObjectTypeReference();
            final Foo newValue = new Foo("new value", 72);
            final CustomObjectDraft<Foo> draft = CustomObjectDraft.ofVersionedDraft(customObject, newValue, typeReference);
            final CustomObjectUpsertCommand<Foo> command = CustomObjectUpsertCommand.of(draft);
            final CustomObject<Foo> updatedCustomObject = execute(command);
            final Foo loadedValue = updatedCustomObject.getValue();
            assertThat(loadedValue).isEqualTo(newValue);
            //end example parsing here
            try {
                execute(command);
                fail("exception should be thrown");
            } catch (final Exception e) {
                assertThat(e.getCause().getCause()).isInstanceOf(ConcurrentModificationException.class);
            }
        });
    }
}