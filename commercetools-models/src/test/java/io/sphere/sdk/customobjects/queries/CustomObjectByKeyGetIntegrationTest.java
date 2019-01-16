package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.carts.CartFixtures;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customobjects.CustomObjectFixtures.withCustomObject;
import static io.sphere.sdk.customobjects.CustomObjectFixtures.withCustomObjectAndCustomClasses;
import static org.assertj.core.api.Assertions.assertThat;


public class CustomObjectByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomObject(client(), existingCustomObject -> {
            final String container = existingCustomObject.getContainer();
            final String key = existingCustomObject.getKey();
            final CustomObjectByKeyGet<Foo> fetch =
                    CustomObjectByKeyGet.of(container, key, Foo.class);
            final CustomObject<Foo> customObject = client().executeBlocking(fetch);
            assertThat(customObject).isNotNull();
            assertThat(customObject).isEqualTo(existingCustomObject);
        });
    }

    @Test
    public void expansionPath() {
        CartFixtures.withCart(client(), cart -> {
            withCustomObjectAndCustomClasses(client(), cart, existingCustomObject -> {
                final String container = existingCustomObject.getContainer();
                final String key = existingCustomObject.getKey();
                final CustomObjectByKeyGet<MyCustomClass> fetch =
                        CustomObjectByKeyGet.of(container, key, MyCustomClass.class);
                CustomObject<MyCustomClass> customObject = client().executeBlocking(fetch);
                assertThat(customObject).isEqualTo(existingCustomObject);
                final ExpansionPath<CustomObject<MyCustomClass>> expansionPath = ExpansionPath.of("value.cartReference");
                CustomObjectByKeyGet<MyCustomClass> expandedFetch = fetch.withExpansionPaths(expansionPath);
                customObject = client().executeBlocking(expandedFetch);
                assertThat(customObject).isNotNull();
                assertThat(customObject.getValue().getCartReference().getObj()).isNotNull();
            });
            return cart;
        });
    }

    @Test
    public void executionForPureJson() throws Exception {
        withCustomObject(client(), existingCustomObject -> {
            final String container = existingCustomObject.getContainer();
            final String key = existingCustomObject.getKey();
            final CustomObjectByKeyGet<JsonNode> fetch = CustomObjectByKeyGet.ofJsonNode(container, key);
            final CustomObject<JsonNode> customObject = client().executeBlocking(fetch);
            assertThat(customObject).isNotNull();
            final JsonNode value = customObject.getValue();
            final String expected = existingCustomObject.getValue().getBar();
            final String actual = value.get("bar").asText("it is not present");
            assertThat(actual).isEqualTo(expected);
        });
    }
}