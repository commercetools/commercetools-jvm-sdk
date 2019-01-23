package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customobjects.commands.CustomObjectDeleteCommand;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.customobjects.queries.CustomObjectQuery;
import io.sphere.sdk.customobjects.queries.MyCustomClass;

import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectFixtures {

    public static final Foo FOO_DEFAULT_VALUE = new Foo("aString", 5L);

    public static void withCustomObject(final BlockingSphereClient client, final Consumer<CustomObject<Foo>> consumer) {
        final CustomObject<Foo> customObject = createCustomObject(client);
        consumer.accept(customObject);
        final DeleteCommand<CustomObject<Foo>> deleteCommand = CustomObjectDeleteCommand.of(customObject, Foo.class);
        client.executeBlocking(deleteCommand);
    }

    //is example
    private static CustomObject<Foo> createCustomObject(final BlockingSphereClient client) {
        final String container = "CustomObjectFixtures";
        final String key = randomKey();
        final Foo value = FOO_DEFAULT_VALUE;
        final CustomObjectDraft<Foo> draft = CustomObjectDraft.ofUnversionedUpsert(container, key, value, Foo.class);
        final CustomObjectUpsertCommand<Foo> createCommand = CustomObjectUpsertCommand.of(draft);
        final CustomObject<Foo> customObject = client.executeBlocking(createCommand);
        assertThat(customObject.getContainer()).isEqualTo(container);
        assertThat(customObject.getKey()).isEqualTo(key);
        final Foo loadedValue = customObject.getValue();
        assertThat(loadedValue).isEqualTo(value);
        //end example parsing here
        return customObject;
    }

    public static void withCustomObjectAndCustomClasses(final BlockingSphereClient client, Cart cart, final Consumer<CustomObject<MyCustomClass>> consumer){
        MyCustomClass myCustomClass = new MyCustomClass("id-1", "description-1", cart.toReference());

        final String container = "CustomObjectFixtures";
        final String key = randomKey();
        final MyCustomClass value = myCustomClass;
        final CustomObjectDraft<MyCustomClass> draft = CustomObjectDraft.ofUnversionedUpsert(container, key, value, MyCustomClass.class);
        final CustomObjectUpsertCommand<MyCustomClass> createCommand = CustomObjectUpsertCommand.of(draft);
        final CustomObject<MyCustomClass> customObject = client.executeBlocking(createCommand);
        assertThat(customObject.getContainer()).isEqualTo(container);
        assertThat(customObject.getKey()).isEqualTo(key);
        consumer.accept(customObject);
        final DeleteCommand<CustomObject<MyCustomClass>> deleteCommand = CustomObjectDeleteCommand.of(customObject, MyCustomClass.class);
        client.executeBlocking(deleteCommand);
    }

    public static void withCustomObject(final BlockingSphereClient client, final String container, final String key, final Consumer<CustomObject<Foo>> consumer) {
        final CustomObject<Foo> customObject = createCustomObjectOfContainerAndKey(client, container, key);
        consumer.accept(customObject);
        final DeleteCommand<CustomObject<Foo>> deleteCommand = CustomObjectDeleteCommand.of(customObject, Foo.class);
        client.executeBlocking(deleteCommand);
    }

    private static CustomObject<Foo> createCustomObjectOfContainerAndKey(final BlockingSphereClient client, final String container, final String key) {
        final Foo value = new Foo("aString", 5L);
        final CustomObjectDraft<Foo> draft = CustomObjectDraft.ofUnversionedUpsert(container, key, value, Foo.class);
        final CustomObjectUpsertCommand<Foo> createCommand = CustomObjectUpsertCommand.of(draft);
        final CustomObject<Foo> customObject = client.executeBlocking(createCommand);
        return customObject;
    }

    public static void dropAll(final BlockingSphereClient client) {
        final CustomObjectQuery<JsonNode> query = CustomObjectQuery.ofJsonNode();
        client.executeBlocking(query).getResults()
                .forEach(item -> {
                    //there is a bug that you can create custom objects with spaces in the container
                    if (!item.getContainer().contains(" ") && !item.getKey().contains(" ")) {
                        final DeleteCommand<CustomObject<JsonNode>> command = CustomObjectDeleteCommand.ofJsonNode(item);
                        client.executeBlocking(command);
                    }
                });
    }

    public static void withCustomObject(final BlockingSphereClient client, final CustomObjectDraft<JsonNode> draft, final Consumer<CustomObject<JsonNode>> c) {
        final CustomObjectUpsertCommand<JsonNode> createCommand = CustomObjectUpsertCommand.of(draft);
        final CustomObject<JsonNode> customObject = client.executeBlocking(createCommand);
        c.accept(customObject);
        client.executeBlocking(CustomObjectDeleteCommand.ofJsonNode(customObject));
    }
}
