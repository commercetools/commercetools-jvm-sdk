package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CustomObjectKeyTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void keyContainsOnlyValidCharacters() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The key \"evil space\" does not have the correct format. Key and container need to match [-_~.a-zA-Z0-9]+.");
        final String value = "hello";
        final String key = "evil space";
        CustomObjectUpsertCommand.of(CustomObjectDraft.of("container", key, value, new TypeReference<CustomObject<String>>() {
        }));
    }

    @Test
    public void containerContainsOnlyValidCharacters() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The container \"evil space\" does not have the correct format. Key and container need to match [-_~.a-zA-Z0-9]+.");
        final String value = "hello";
        final String key = "key";
        final String container = "evil space";
        CustomObjectUpsertCommand.of(CustomObjectDraft.of(container, key, value, new TypeReference<CustomObject<String>>() {
        }));
    }
}