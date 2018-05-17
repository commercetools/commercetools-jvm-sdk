package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyGet;
import io.sphere.sdk.test.IntegrationTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Test;

import java.util.function.Function;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class CustomObjectDeleteCommandIntegrationTest extends IntegrationTest {

    public static final String CONTAINER = CustomObjectDeleteCommandIntegrationTest.class.getSimpleName();

    @Test
    public void deletionWithoutTypeReference() throws Exception {
        testOmitResult(customObject -> CustomObjectDeleteCommand.ofJsonNode(customObject.getContainer(), customObject.getKey()));
    }

    @Test
    public void deletionWithoutTypeReferenceAndDataErasure() throws Exception {
        testOmitResult(customObject -> CustomObjectDeleteCommand.ofJsonNode(customObject.getContainer(), customObject.getKey(),true));
    }


    @Test
    public void deletionWithoutTypeReferenceAsObject() throws Exception {
        testOmitResult(customObject -> CustomObjectDeleteCommand.ofJsonNode(customObject));
    }

    @Test
    public void demo() {
        final String key = randomKey();
        final Foo foo = new Foo("bar", 7L);
        final CustomObject<Foo> customObject = client().executeBlocking(CustomObjectUpsertCommand
                .of(CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, foo, Foo.class)));

        final CustomObject<Foo> deletedObject = client().executeBlocking(CustomObjectDeleteCommand.of(CONTAINER, key, Foo.class));

        assertThat(deletedObject.getValue()).isEqualTo(foo);
        assertThat(client().executeBlocking(CustomObjectByKeyGet.of(CONTAINER, key, Foo.class)))
                .as("get request")
                .isNull();
    }

    @Test
    public void demoById() {
        final String key = randomKey();
        final Foo foo = new Foo("bar", 7L);
        final CustomObject<Foo> customObject = client().executeBlocking(CustomObjectUpsertCommand
                .of(CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, foo, Foo.class)));
        final String id = customObject.getId();
        final Long version = customObject.getVersion();

        final CustomObject<Foo> deletedObject = client().executeBlocking(CustomObjectDeleteCommand.of(id, version, Foo.class));

        assertThat(deletedObject.getValue()).isEqualTo(foo);
        assertThat(client().executeBlocking(CustomObjectByKeyGet.of(CONTAINER, key, Foo.class)))
                .as("get request")
                .isNull();
    }

    private void testOmitResult(final Function<CustomObject<String>, DeleteCommand<CustomObject<JsonNode>>> f) {
        final String value = "hello";
        final String key = "storeFlatString";
        final CustomObjectUpsertCommand<String> command = CustomObjectUpsertCommand.of(CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, value, String.class));
        final CustomObject<String> customObject = client().executeBlocking(command);
        final CustomObject<JsonNode> result = client().executeBlocking(f.apply(customObject));
        assertThat(result.getId()).isEqualTo(customObject.getId());
    }
}