package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.customobjects.CustomObjectFixtures.withCustomObject;
import static org.assertj.core.api.Assertions.assertThat;


public class CustomObjectByKeyFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomObject(client(), existingCustomObject -> {
            final String container = existingCustomObject.getContainer();
            final String key = existingCustomObject.getKey();
            final CustomObjectByKeyFetch<Foo> fetch =
                    CustomObjectByKeyFetch.of(container, key, Foo.customObjectTypeReference());
            final Optional<CustomObject<Foo>> customObjectOptional = execute(fetch);
            assertThat(customObjectOptional).isPresent();
            final Reference<CustomObject<JsonNode>> actual = customObjectOptional.get().toReference();
            assertThat(actual).isEqualTo(existingCustomObject.toReference());
        });
    }

    @Test
    public void executionForPureJson() throws Exception {
        withCustomObject(client(), existingCustomObject -> {
            final String container = existingCustomObject.getContainer();
            final String key = existingCustomObject.getKey();
            final CustomObjectByKeyFetch<JsonNode> fetch = CustomObjectByKeyFetch.of(container, key);
            final Optional<CustomObject<JsonNode>> customObjectOptional = execute(fetch);
            assertThat(customObjectOptional).isPresent();
            final JsonNode value = customObjectOptional.get().getValue();
            final String expected = existingCustomObject.getValue().getBar();
            final String actual = value.get("bar").asText("it is not present");
            assertThat(actual).isEqualTo(expected);
        });
    }
}