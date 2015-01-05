package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.commands.CustomObjectDeleteByContainerAndKeyCommand;
import io.sphere.sdk.customobjects.demo.Foo;

import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class CustomObjectFixtures {
    public static void withCustomObject(final TestClient client, final Consumer<CustomObject<Foo>> consumer) {
        final CustomObject<Foo> customObject = createCustomObject(client);
        consumer.accept(customObject);
        final CustomObjectDeleteByContainerAndKeyCommand<CustomObject<Foo>> deleteCommand = CustomObjectDeleteByContainerAndKeyCommand.of(customObject, Foo.customObjectTypeReference());
        client.execute(deleteCommand);
    }

    //is example
    private static CustomObject<Foo> createCustomObject(final TestClient client) {
        final String container = "CustomObjectFixtures";
        final String key = randomKey();
        final Foo value = new Foo("aString", 5);
        final TypeReference<CustomObject<Foo>> typeReference = Foo.customObjectTypeReference();
        final CustomObjectDraft<Foo> draft = CustomObjectDraft.of(container, key, value, typeReference);
        final CustomObjectUpsertCommand<Foo> createCommand = CustomObjectUpsertCommand.of(draft);
        final CustomObject<Foo> customObject = client.execute(createCommand);
        assertThat(customObject.getContainer()).isEqualTo(container);
        assertThat(customObject.getKey()).isEqualTo(key);
        assertThat(customObject.getValue()).isEqualTo(value);
        //end example parsing here
        return customObject;
    }

    public static void withCustomObject(final TestClient client, final String container, final String key, final Consumer<CustomObject<Foo>> consumer) {
        final CustomObject<Foo> customObject = createCustomObject(client);
        consumer.accept(customObject);
        final CustomObjectDeleteByContainerAndKeyCommand<CustomObject<Foo>> deleteCommand = CustomObjectDeleteByContainerAndKeyCommand.of(customObject, Foo.customObjectTypeReference());
        client.execute(deleteCommand);
    }

    private static CustomObject<Foo> createCustomObjectOfContainerAndKey(final TestClient client, final String container, final String key) {
        final Foo value = new Foo("aString", 5);
        final TypeReference<CustomObject<Foo>> typeReference = Foo.customObjectTypeReference();
        final CustomObjectDraft<Foo> draft = CustomObjectDraft.of(container, key, value, typeReference);
        final CustomObjectUpsertCommand<Foo> createCommand = CustomObjectUpsertCommand.of(draft);
        final CustomObject<Foo> customObject = client.execute(createCommand);
        return customObject;
    }
}
