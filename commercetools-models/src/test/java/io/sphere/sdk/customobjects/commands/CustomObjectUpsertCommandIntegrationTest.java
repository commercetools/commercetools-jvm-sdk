package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.customobjects.demo.BinaryData;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyGet;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.test.IntegrationTest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectUpsertCommandIntegrationTest extends IntegrationTest {

    public static final String CONTAINER = CustomObjectUpsertCommandIntegrationTest.class.getSimpleName();

    @BeforeClass
    public static void clean() {
        final CustomObject<Foo> nullableObject = client().executeBlocking(CustomObjectByKeyGet.of("demo-container", "demo-key", Foo.class));
        if (nullableObject != null) {
            client().executeBlocking(CustomObjectDeleteCommand.ofJsonNode(nullableObject));
        }
    }

    @Test
    public void createSimpleNew() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), co -> {

        });
    }

    @Test
    public void pureJson() throws Exception {
        final ObjectMapper objectMapper = SphereJsonUtils.newObjectMapper();//you should cache this one
        final ObjectNode objectNode = objectMapper.createObjectNode()
                .put("bar", " a string")
                .put("baz", 5L);
        final String key = "pure-json";
        final CustomObjectUpsertCommand<JsonNode> command =
                CustomObjectUpsertCommand.of(CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, objectNode));
        final CustomObject<JsonNode> customObject = client().executeBlocking(command);
        assertThat(customObject.getValue().get("bar").asText("default value")).isEqualTo(" a string");
        assertThat(customObject.getValue().get("baz").asLong(0)).isEqualTo(5);
    }

    @Test
    public void storyBinaryData() throws Exception {
        final String name = "hello.pdf";
        final BinaryData value = new BinaryData(name, IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(name)));
        final String key = "storyBinaryData";
        final CustomObjectUpsertCommand<BinaryData> command = CustomObjectUpsertCommand.of(CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, value, BinaryData.class));
        final BinaryData loadedValue = client().executeBlocking(command).getValue();
        assertThat(loadedValue).isEqualTo(value);
    }

    @Test
    public void storeFlatString() throws Exception {
        final String value = "hello";
        final String key = "storeFlatString";
        final CustomObjectUpsertCommand<String> command = CustomObjectUpsertCommand.of(CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, value, String.class));
        final String loadedValue = client().executeBlocking(command).getValue();
        assertThat(loadedValue).isEqualTo(value);
    }

    @Test
    public void storeLong() throws Exception {
        final long value = 23;
        final String key = "storeLong";
        final CustomObjectUpsertCommand<Long> command = CustomObjectUpsertCommand.of(CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, value, Long.class));
        final long loadedValue = client().executeBlocking(command).getValue();
        assertThat(loadedValue).isEqualTo(value);
    }

    @Test
    public void updateWithoutVersion() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), customObject -> {
            final Foo newValue = new Foo("new value", 72L);
            final CustomObjectDraft<Foo> draft = CustomObjectDraft.ofUnversionedUpdate(customObject, newValue, Foo.class);
            final CustomObjectUpsertCommand<Foo> createCommand = CustomObjectUpsertCommand.of(draft);
            final CustomObject<Foo> updatedCustomObject = client().executeBlocking(createCommand);
            final Foo loadedValue = updatedCustomObject.getValue();
            assertThat(loadedValue).isEqualTo(newValue);
        });
    }

    @Test(expected = ConcurrentModificationException.class)
    public void updateWithVersion() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), customObject -> {
            final Foo newValue = new Foo("new value", 72L);
            final CustomObjectDraft<Foo> draft = CustomObjectDraft.ofVersionedUpdate(customObject, newValue, Foo.class);
            final CustomObjectUpsertCommand<Foo> command = CustomObjectUpsertCommand.of(draft);
            final CustomObject<Foo> updatedCustomObject = client().executeBlocking(command);
            final Foo loadedValue = updatedCustomObject.getValue();
            assertThat(loadedValue).isEqualTo(newValue);
            //end example parsing here
            client().executeBlocking(command);
        });
    }

    @Test
    public void createByJson() {
        final CustomObjectDraft<JsonNode> customObjectDraft = SphereJsonUtils.readObjectFromResource("drafts-tests/customObject.json", new TypeReference<CustomObjectDraft<JsonNode>>() {
        });
        CustomObjectFixtures.withCustomObject(client(), customObjectDraft, customObject -> {
            assertThat(customObject.getKey()).isEqualTo("demo-key");
            assertThat(customObject.getContainer()).isEqualTo("demo-container");
            final JsonNode actual = customObject.getValue();
            assertThat(actual.get("baz").asInt()).isEqualTo(22);
            assertThat(actual.get("bar").asText()).isEqualTo("hello");
        });
    }
}