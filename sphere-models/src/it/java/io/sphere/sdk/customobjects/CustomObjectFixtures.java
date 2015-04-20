package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.commands.CustomObjectDeleteCommand;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.customobjects.queries.CustomObjectQuery;
import io.sphere.sdk.queries.PagedQueryResult;

import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class CustomObjectFixtures {

    public static final Foo FOO_DEFAULT_VALUE = new Foo("aString", 5);

    public static void withCustomObject(final TestClient client, final Consumer<CustomObject<Foo>> consumer) {
        final CustomObject<Foo> customObject = createCustomObject(client);
        consumer.accept(customObject);
        final DeleteCommand<CustomObject<Foo>> deleteCommand = CustomObjectDeleteCommand.of(customObject, Foo.customObjectTypeReference());
        client.execute(deleteCommand);
    }

    //is example
    private static CustomObject<Foo> createCustomObject(final TestClient client) {
        final String container = "CustomObjectFixtures";
        final String key = randomKey();
        final Foo value = FOO_DEFAULT_VALUE;
        final TypeReference<CustomObject<Foo>> typeReference = Foo.customObjectTypeReference();
        final CustomObjectDraft<Foo> draft = CustomObjectDraft.ofUnversionedUpsert(container, key, value, typeReference);
        final CustomObjectUpsertCommand<Foo> createCommand = CustomObjectUpsertCommand.of(draft);
        final CustomObject<Foo> customObject = client.execute(createCommand);
        assertThat(customObject.getContainer()).isEqualTo(container);
        assertThat(customObject.getKey()).isEqualTo(key);
        final Foo loadedValue = customObject.getValue();
        assertThat(loadedValue).isEqualTo(value);
        //end example parsing here
        return customObject;
    }

    public static void withCustomObject(final TestClient client, final String container, final String key, final Consumer<CustomObject<Foo>> consumer) {
        final CustomObject<Foo> customObject = createCustomObjectOfContainerAndKey(client, container, key);
        consumer.accept(customObject);
        final DeleteCommand<CustomObject<Foo>> deleteCommand = CustomObjectDeleteCommand.of(customObject, Foo.customObjectTypeReference());
        client.execute(deleteCommand);
    }

    private static CustomObject<Foo> createCustomObjectOfContainerAndKey(final TestClient client, final String container, final String key) {
        final Foo value = new Foo("aString", 5);
        final TypeReference<CustomObject<Foo>> typeReference = Foo.customObjectTypeReference();
        final CustomObjectDraft<Foo> draft = CustomObjectDraft.ofUnversionedUpsert(container, key, value, typeReference);
        final CustomObjectUpsertCommand<Foo> createCommand = CustomObjectUpsertCommand.of(draft);
        final CustomObject<Foo> customObject = client.execute(createCommand);
        return customObject;
    }

    public static void dropAll(final TestClient client) {
        final CustomObjectQuery<Object> query = CustomObjectQuery.of(new TypeReference<PagedQueryResult<CustomObject<Object>>>() {});
        client.execute(query).getResults()
                .forEach(item -> {
                    //there is a bug that you can create custom objects with spaces in the container
                    if (!item.getContainer().contains(" ") && !item.getKey().contains(" ")) {
                        final TypeReference<CustomObject<Object>> typeReference = new TypeReference<CustomObject<Object>>() {
                        };
                        final DeleteCommand<CustomObject<Object>> command = CustomObjectDeleteCommand.of(item, typeReference);
                        client.execute(command);
                    }
                });
    }
}
