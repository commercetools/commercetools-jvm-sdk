package io.sphere.sdk.customobjects.migrations;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.migrations.version3.*;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyFetch;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectsMigrationsTest extends IntegrationTest {


    private static final String CONTAINER = CustomObjectsMigrationsTest.class.getSimpleName();

    @Test
    public void addingAField() throws Exception {
        final String key = "addingAField";
        final CustomObjectDraft<io.sphere.sdk.customobjects.migrations.version1.Xyz> draft = CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, new io.sphere.sdk.customobjects.migrations.version1.Xyz("foo"), io.sphere.sdk.customobjects.migrations.version1.Xyz.customObjectTypeReference());
        final CustomObject<io.sphere.sdk.customobjects.migrations.version1.Xyz> customObject = execute(CustomObjectUpsertCommand.of(draft));

        final CustomObject<io.sphere.sdk.customobjects.migrations.version2.Xyz> xyz2CustomObject = execute(CustomObjectByKeyFetch.of(CONTAINER, key, io.sphere.sdk.customobjects.migrations.version2.Xyz.customObjectTypeReference()));
        assertThat(xyz2CustomObject.getValue().getBar()).isEmpty();

        final CustomObjectUpsertCommand<io.sphere.sdk.customobjects.migrations.version2.Xyz> upsertCommand =
                CustomObjectUpsertCommand.of(CustomObjectDraft.ofVersionedUpdate(xyz2CustomObject, new io.sphere.sdk.customobjects.migrations.version2.Xyz("foo", Optional.of("bar")), io.sphere.sdk.customobjects.migrations.version2.Xyz.customObjectTypeReference()));

        assertThat(execute(upsertCommand).getValue().getBar()).contains("bar");
    }

    @Test
    public void removingAField() throws Exception {
        final String key = "removingAField";
        final CustomObjectUpsertCommand<io.sphere.sdk.customobjects.migrations.version2.Xyz> upsertCommand =
                CustomObjectUpsertCommand.of(CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, new io.sphere.sdk.customobjects.migrations.version2.Xyz("foo", Optional.of("bar")), io.sphere.sdk.customobjects.migrations.version2.Xyz.customObjectTypeReference()));
        execute(upsertCommand);
        final CustomObject<Xyz> xyz3CustomObject = execute(CustomObjectByKeyFetch.of(CONTAINER, key, Xyz.customObjectTypeReference()));
        assertThat(xyz3CustomObject.getValue().getBar()).contains("bar");
    }

    @Test
    public void optionalExample() throws Exception {
        final io.sphere.sdk.customobjects.migrations.version2.Xyz xyz = new io.sphere.sdk.customobjects.migrations.version2.Xyz("foo", Optional.of("bar"));
        final String bar = xyz.getBar().orElse("default value");
    }

    @Test
    public void migration() throws Exception {
        final String key = "migration";
        final CustomObjectDraft<io.sphere.sdk.customobjects.migrations.version1.Uvw> draft = CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, new io.sphere.sdk.customobjects.migrations.version1.Uvw("a&b", "anotherField"), io.sphere.sdk.customobjects.migrations.version1.Uvw.customObjectTypeReference());
        final CustomObject<io.sphere.sdk.customobjects.migrations.version1.Uvw> objectSchema1 = execute(CustomObjectUpsertCommand.of(draft));
        assertThat(objectSchema1.getValue().getFoo()).isEqualTo("a&b");

        final CustomObject<Uvw> uvwCustomObjectSchema1 = execute(CustomObjectByKeyFetch.of(CONTAINER, key, Uvw.customObjectTypeReference()));
        assertThat(uvwCustomObjectSchema1.getValue()).isInstanceOf(UvwSchemaVersion1.class);
        assertThat(uvwCustomObjectSchema1.getValue().getFoo()).isEqualTo(new Foo("a", "b"));

        final CustomObjectDraft<Uvw> draftSchema2 = CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, uvwCustomObjectSchema1.getValue(), Uvw.customObjectTypeReference());
        final CustomObject<Uvw> schema2Object = execute(CustomObjectUpsertCommand.of(draftSchema2));
        assertThat(uvwCustomObjectSchema1.getValue()).isInstanceOf(UvwSchemaVersion2.class);
        assertThat(uvwCustomObjectSchema1.getValue().getFoo()).isEqualTo(new Foo("a", "b"));
    }

    @Test
    public void exampleForMigrationCall() throws Exception {
        final CustomObjectByKeyFetch<Uvw> fetch = CustomObjectByKeyFetch.of("container", "key", Uvw.customObjectTypeReference());
    }
}
