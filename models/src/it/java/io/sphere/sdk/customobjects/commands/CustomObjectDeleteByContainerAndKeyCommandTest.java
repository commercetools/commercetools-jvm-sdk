package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.function.Function;

import static org.fest.assertions.Assertions.assertThat;

public class CustomObjectDeleteByContainerAndKeyCommandTest extends IntegrationTest {

    public static final String CONTAINER = CustomObjectDeleteByContainerAndKeyCommandTest.class.getSimpleName();

    @Test
    public void deletionWithoutTypeReference() throws Exception {
        testOmitResult(customObject -> CustomObjectDeleteByContainerAndKeyCommand.of(customObject.getContainer(), customObject.getKey()));
    }

    @Test
    public void deletionWithoutTypeReferenceAsObject() throws Exception {
        testOmitResult(customObject -> CustomObjectDeleteByContainerAndKeyCommand.of(customObject));
    }

    private void testOmitResult(final Function<CustomObject<String>, CustomObjectDeleteByContainerAndKeyCommand<CustomObject<JsonNode>>> f) {
        final String value = "hello";
        final String key = "storeFlatString";
        final CustomObjectUpsertCommand<String> command = CustomObjectUpsertCommand.of(CustomObjectDraft.of(CONTAINER, key, value, new TypeReference<CustomObject<String>>() {
        }));
        final CustomObject<String> customObject = execute(command);
        final CustomObject<JsonNode> result = execute(f.apply(customObject));
        assertThat(result.getId()).isEqualTo(customObject.getId());
    }
}