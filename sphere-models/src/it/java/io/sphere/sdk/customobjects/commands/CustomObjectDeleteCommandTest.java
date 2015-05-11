package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectDeleteCommandTest extends IntegrationTest {

    public static final String CONTAINER = CustomObjectDeleteCommandTest.class.getSimpleName();

    @Test
    public void deletionWithoutTypeReference() throws Exception {
        testOmitResult(customObject -> CustomObjectDeleteCommand.of(customObject.getContainer(), customObject.getKey()));
    }

    @Test
    public void deletionWithoutTypeReferenceAsObject() throws Exception {
        testOmitResult(customObject -> CustomObjectDeleteCommand.of(customObject));
    }

    private void testOmitResult(final Function<CustomObject<String>, DeleteCommand<CustomObject<JsonNode>>> f) {
        final String value = "hello";
        final String key = "storeFlatString";
        final CustomObjectUpsertCommand<String> command = CustomObjectUpsertCommand.of(CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, value, new TypeReference<CustomObject<String>>() {
        }));
        final CustomObject<String> customObject = execute(command);
        final CustomObject<JsonNode> result = execute(f.apply(customObject));
        assertThat(result.getId()).isEqualTo(customObject.getId());
    }
}