package io.sphere.sdk.customobjects;

import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CustomObjectKeyTest {
    @Test
    public void keyContainsOnlyValidCharacters() throws Exception {
        final String value = "hello";
        final String key = "evil space";

        final Throwable throwable = catchThrowable(() -> CustomObjectUpsertCommand.of(CustomObjectDraft.ofUnversionedUpsert("container", key, value, String.class)));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The key \"evil space\" does not have the correct format. Key and container need to match [-_~.a-zA-Z0-9]+.");
    }

    @Test
    public void containerContainsOnlyValidCharacters() throws Exception {
        final String value = "hello";
        final String key = "key";
        final String container = "evil space";

        final Throwable throwable = catchThrowable(() ->
                CustomObjectUpsertCommand.of(CustomObjectDraft.ofUnversionedUpsert(container, key, value, String.class)));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The container \"evil space\" does not have the correct format. Key and container need to match [-_~.a-zA-Z0-9]+.");
    }


}