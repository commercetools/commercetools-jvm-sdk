package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static org.fest.assertions.Assertions.assertThat;


public class CustomObjectFetchByKeyTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), co -> {
            final CustomObjectFetchByKey<Foo> fetch = CustomObjectFetchByKey.of(co.getContainer(), co.getKey(), Foo.customObjectTypeReference());
            final Optional<CustomObject<Foo>> customObjectOptional = execute(fetch);
            assertThat(customObjectOptional).isPresent();
            assertThat(customObjectOptional.get().toReference()).isEqualTo(co.toReference());
        });
    }
}